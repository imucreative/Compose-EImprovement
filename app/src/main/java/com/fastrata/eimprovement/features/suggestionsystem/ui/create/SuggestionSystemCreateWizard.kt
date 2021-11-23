package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.databinding.ActivitySuggestionSystemCreateWizardBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.approval.ui.ListApprovalHistoryStatusSsFragment
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
import com.fastrata.eimprovement.features.suggestionsystem.ui.SuggestionSystemViewModel
import com.fastrata.eimprovement.ui.setToolbar
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import com.google.gson.Gson
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject
import android.view.View.*
import com.google.android.material.snackbar.Snackbar

class SuggestionSystemCreateWizard : AppCompatActivity(), HasSupportFragmentInjector, Injectable {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivitySuggestionSystemCreateWizardBinding
    private lateinit var toolbarBinding: ToolbarBinding
    private var ssNo: String = ""
    private var ssAction: String = ""
    private lateinit var viewModel: SuggestionSystemViewModel
    private var maxStep = 6
    private var currentStep = 1
    private var source: String = SS_CREATE
    private lateinit var notification: HelperNotification
    private val gson = Gson()
    private var data: SuggestionSystemCreateModel? = null
    private var userId = 0
    private var orgId = 0
    private var warehouseId = 0
    private var headId = 0

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    private val args: SuggestionSystemCreateWizardArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySuggestionSystemCreateWizardBinding.inflate(layoutInflater)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        setContentView(binding.root)

        AndroidInjection.inject(this)
        viewModel = injectViewModel(viewModelFactory)

        val argsTitle   = args.toolbarTitle!!
        val argsAction  = args.action
        val argsIdSs    = args.idSs
        val argsSsNo    = args.ssNo
        val statusProposal = args.statusProposal

        userId      = HawkUtils().getDataLogin().USER_ID
        orgId       = HawkUtils().getDataLogin().ORG_ID!!
        warehouseId = HawkUtils().getDataLogin().WAREHOUSE_ID!!
        headId      = HawkUtils().getDataLogin().DIRECT_MANAGER_ID!!

        ssAction = argsAction

        //if (ssAction == APPROVE) {
        //    maxStep += 1
        //}

        when (argsAction) {
            EDIT, DETAIL, APPROVE, SUBMIT_PROPOSAL -> {
                ssNo = argsSsNo

                source = SS_DETAIL_DATA
                viewModel.setDetailSs(argsIdSs, userId)
                viewModel.getDetailSsItem.observeEvent(this) { resultObserve ->
                    resultObserve.observe(this, { result ->
                        if (result != null) {
                            when (result.status) {
                                Result.Status.LOADING -> {
                                    HelperLoading.displayLoadingWithText(this,"",false)
                                    binding.bottomNavigationBar.visibility = GONE

                                    Timber.d("###-- Loading getDetailSsItem")
                                }
                                Result.Status.SUCCESS -> {
                                    HelperLoading.hideLoading()
                                    binding.bottomNavigationBar.visibility = VISIBLE

                                    HawkUtils().setTempDataCreateSs(
                                        id = result.data?.data?.get(0)?.id,
                                        ssNo = result.data?.data?.get(0)?.ssNo,
                                        title = result.data?.data?.get(0)?.title,
                                        listCategory = result.data?.data?.get(0)?.categoryImprovement,
                                        name = result.data?.data?.get(0)?.name,
                                        nik = result.data?.data?.get(0)?.nik,
                                        branchCode = result.data?.data?.get(0)?.branchCode,
                                        branch = result.data?.data?.get(0)?.branch,
                                        department = result.data?.data?.get(0)?.department,
                                        directMgr = result.data?.data?.get(0)?.directMgr,
                                        suggestion = result.data?.data?.get(0)?.suggestion?.let {
                                            HtmlCompat.fromHtml(
                                                it, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                                        },
                                        problem = result.data?.data?.get(0)?.problem?.let {
                                            HtmlCompat.fromHtml(
                                                it, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                                        },
                                        statusImplementation = result.data?.data?.get(0)?.statusImplementation,
                                        teamMember = result.data?.data?.get(0)?.teamMember,
                                        attachment = result.data?.data?.get(0)?.attachment,
                                        statusProposal = result.data?.data?.get(0)?.statusProposal,
                                        proses = result.data?.data?.get(0)?.proses,
                                        result = result.data?.data?.get(0)?.result,

                                        headId = result.data?.data?.get(0)?.headId,
                                        userId = result.data?.data?.get(0)?.userId,
                                        orgId = result.data?.data?.get(0)?.orgId,
                                        warehouseId = result.data?.data?.get(0)?.warehouseId,
                                        historyApproval = result.data?.data?.get(0)?.historyApproval,

                                        activityType = SS,
                                        submitType = if (argsAction == EDIT) 2 else 1,
                                        comment = "",

                                        source = SS_DETAIL_DATA
                                    )

                                    initToolbar(argsTitle)
                                    initComponent()
                                    Timber.d("###-- Success getDetailSsItem")
                                }
                                Result.Status.ERROR -> {
                                    HelperLoading.displayLoadingWithText(this,"",false)
                                    binding.bottomNavigationBar.visibility = GONE
                                    Timber.d("###-- Error getDetailSsItem")
                                }

                            }
                        }
                    })
                }
            }
            ADD -> {
                ssNo = ""

                source = SS_CREATE
                // ini ambil dari session
                HawkUtils().setTempDataCreateSs(
                    id = 0,
                    ssNo = "",
                    name = HawkUtils().getDataLogin().USER_NAME,
                    nik = HawkUtils().getDataLogin().NIK,
                    branchCode = HawkUtils().getDataLogin().BRANCH_CODE,
                    branch = HawkUtils().getDataLogin().BRANCH,
                    department = HawkUtils().getDataLogin().DEPARTMENT,
                    directMgr = HawkUtils().getDataLogin().DIRECT_MANAGER,
                    statusProposal = statusProposal,
                    headId = headId,
                    userId = userId,
                    orgId = orgId,
                    warehouseId = warehouseId,
                    proses = "",
                    result = "",
                    activityType = SS,
                    submitType = 1,
                    comment = "",
                    source = SS_CREATE
                )

                initToolbar(argsTitle)
                initComponent()
            }
        }

        Tools.setSystemBarColor(this, R.color.colorMainEImprovement, this)
        Tools.setSystemBarLight(this)
    }

    private fun initComponent() {
        notification = HelperNotification()
        binding.apply {
            lytBack.setOnClickListener {
                backStep(currentStep)
                bottomProgressDots(currentStep)
            }

            lytNext.setOnClickListener {
                nextStep(currentStep)
                bottomProgressDots(currentStep)
            }

            if (currentStep == 1) {
                lytBack.visibility = INVISIBLE
            }

            bottomProgressDots(currentStep)

            val mFragmentManager = supportFragmentManager
            val mHomeFragment = SuggestionSystemStep1Fragment()
            val fragment = mFragmentManager.findFragmentByTag(SuggestionSystemStep1Fragment::class.java.simpleName)

            if(fragment !is SuggestionSystemStep1Fragment){
                val args = Bundle()
                args.putString(SS_DETAIL_DATA, ssNo)
                args.putString(ACTION_DETAIL_DATA, ssAction)
                mHomeFragment.arguments = args
                mFragmentManager
                    .beginTransaction()
                    .add(R.id.frame_container, mHomeFragment, SuggestionSystemStep1Fragment::class.java.simpleName)
                    .commit()
            }

            lytAccept.setOnClickListener {
                buttonAction(
                    1, R.color.green_A700,
                    "Setuju",
                    "Apakah Anda yakin ingin Menyetujui pengajuan ini?",
                    "Setuju"
                )
            }
            lytRevision.setOnClickListener {
                buttonAction(
                    2, R.color.blue_A700,
                    "Revisi",
                    "Apakah Anda yakin ingin Merevisi pengajuan ini?",
                    "Revisi"
                )
            }
            lytReject.setOnClickListener {
                buttonAction(
                    3, R.color.red_A700,
                    "Tolak",
                    "Apakah Anda yakin ingin Menolak pengajuan ini?",
                    "Tolak"
                )
            }
        }
    }

    private fun buttonAction(key: Int, color: Int, title: String, description: String, buttonString: String) {
        binding.apply {
            notification.showNotificationYesNoWithComment(
                this@SuggestionSystemCreateWizard,
                applicationContext, color, title, description, buttonString,
                resources.getString(R.string.cancel),
                object : HelperNotification.CallBackNotificationYesNoWithComment {
                    override fun onNotificationNo() {

                    }

                    override fun onNotificationYes(comment: String) {
                        data = HawkUtils().getTempDataCreateSs(source)
                        if (comment != "") {
                            val updateProposal = SuggestionSystemCreateModel(
                                data?.id, data?.ssNo, data?.date, data?.name,
                                userId = userId,
                                data?.nik, data?.statusImplementation, data?.title, data?.orgId, data?.warehouseId,
                                data?.branchCode, data?.branch, data?.subBranch, data?.headId, data?.directMgr,
                                data?.problem, data?.suggestion, data?.attachment, data?.categoryImprovement,
                                data?.department, data?.teamMember, data?.statusProposal, data?.proses,
                                data?.result, data?.historyApproval,
                                activityType = SS, submitType = key, comment = comment
                            )
                            update(updateProposal)
                        } else {
                            Snackbar.make(binding.root, resources.getString(R.string.wrong_field), Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        }
    }

    private fun initToolbar(title: String) {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_black)

        setToolbar(this, toolbar, title)
    }

    private fun currentStepCondition(currentStep: Int) {
        val mFragmentManager = supportFragmentManager
        when (currentStep) {
            1 -> {
                Timber.e("### step 1 = $currentStep")
                val fragment = SuggestionSystemStep1Fragment()
                val args = Bundle()
                args.putString(SS_DETAIL_DATA, ssNo)
                args.putString(ACTION_DETAIL_DATA, ssAction)
                fragment.arguments = args
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, fragment, SuggestionSystemStep1Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            2 -> {
                Timber.e("### step 2 = $currentStep")
                val fragment = SuggestionSystemStep2Fragment()
                val args = Bundle()
                args.putString(SS_DETAIL_DATA, ssNo)
                args.putString(ACTION_DETAIL_DATA, ssAction)
                fragment.arguments = args
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, fragment, SuggestionSystemStep2Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            3 -> {
                Timber.e("### step 3 = $currentStep")
                val fragment = SuggestionSystemStep3Fragment()
                val args = Bundle()
                args.putString(SS_DETAIL_DATA, ssNo)
                args.putString(ACTION_DETAIL_DATA, ssAction)
                fragment.arguments = args
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, fragment, SuggestionSystemStep3Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            4 ->{
                Timber.e("### step 4 = $currentStep")
                val fragment = SuggestionSystemStep4Fragment()
                val args = Bundle()
                args.putString(SS_DETAIL_DATA, ssNo)
                args.putString(ACTION_DETAIL_DATA, ssAction)
                fragment.arguments = args
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, fragment, SuggestionSystemStep4Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }

            5 -> {
                Timber.e("### step 5 = $currentStep")
                val fragment = SuggestionSystemStep5Fragment()
                val args = Bundle()
                args.putString(SS_DETAIL_DATA, ssNo)
                args.putString(ACTION_DETAIL_DATA, ssAction)
                fragment.arguments = args
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, fragment, SuggestionSystemStep5Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            6 -> {
                Timber.e("### step 6 = $currentStep")
                val fragment = ListApprovalHistoryStatusSsFragment()
                val args = Bundle()
                args.putString(SS_DETAIL_DATA, ssNo)
                args.putString(ACTION_DETAIL_DATA, ssAction)
                fragment.arguments = args
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, fragment, ListApprovalHistoryStatusSsFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }

    private lateinit var ssCreateCallback: SuggestionSystemCreateCallback
    fun setSsCreateCallback(ssCreateCallback: SuggestionSystemCreateCallback) {
        this.ssCreateCallback = ssCreateCallback
    }

    private fun nextStep(progress: Int) {
        val status =  ssCreateCallback.onDataPass()
        if (status) {
            if (progress < maxStep) {
                currentStep = progress + 1
                currentStepCondition(currentStep)

                binding.apply {
                    lytBack.visibility = VISIBLE
                    lytNext.visibility = VISIBLE

                    if (ssAction == DETAIL) {
                        if (currentStep == maxStep) {
                            lytNext.visibility = INVISIBLE
                            binding.actionBottom.visibility = GONE
                        }
                    } else if (ssAction == APPROVE && (data?.statusProposal?.id == 3 || data?.statusProposal?.id == 8)) {
                        if (currentStep == maxStep) {
                            lytNext.visibility = INVISIBLE
                            binding.actionBottom.visibility = VISIBLE
                        }
                    }
                }
            } else {
                //CoroutineScope(Dispatchers.Default).launch {
                data = HawkUtils().getTempDataCreateSs(source)
                val convertToJson = gson.toJson(data)

                Timber.e("### Data form input : $convertToJson")
                Timber.e("${data?.statusProposal}")

                var initialTypeProposal = ""
                var buttonInitialTypeProposal = ""

                when (data?.statusProposal?.id) {
                    1, 4 -> {
                        initialTypeProposal = "Submit"
                        buttonInitialTypeProposal = "Submit"
                    }
                    2 -> {
                        initialTypeProposal = "Check"
                        buttonInitialTypeProposal = "Check"
                    }
                    5 -> {
                        initialTypeProposal = "Implementation"
                        buttonInitialTypeProposal = "Implementation"
                    }
                    6 -> {
                        initialTypeProposal = "Submit Laporan Akhir"
                        buttonInitialTypeProposal = "Submit"
                    }
                    7, 9 -> {
                        initialTypeProposal = "Review"
                        buttonInitialTypeProposal = "Review"
                    }
                }

                binding.apply {
                    notification.shownotificationyesno(
                        this@SuggestionSystemCreateWizard,
                        applicationContext,
                        R.color.blue_500,
                        initialTypeProposal,
                        resources.getString(R.string.submit_desc),
                        buttonInitialTypeProposal,
                        resources.getString(R.string.cancel),
                        object : HelperNotification.CallBackNotificationYesNo {
                            override fun onNotificationNo() {

                            }

                            override fun onNotificationYes() {
                                if (data?.ssNo.isNullOrEmpty()) {
                                    submit(data!!)
                                } else {
                                    update(data!!)
                                }
                            }
                        }
                    )
                }
                // }
            }
        }
    }

    private fun submit(data: SuggestionSystemCreateModel){
        viewModel.setPostSubmitCreateSs(data)

        viewModel.postSubmitCreateSs.observeEvent(this@SuggestionSystemCreateWizard) { resultObserve ->
            resultObserve.observe(this@SuggestionSystemCreateWizard, { result ->
                if (result != null) {
                    when (result.status) {
                        Result.Status.LOADING -> {
                            HelperLoading.displayLoadingWithText(this@SuggestionSystemCreateWizard,"",false)
                            Timber.d("###-- Loading postSubmitCreateSs")
                        }
                        Result.Status.SUCCESS -> {
                            HelperLoading.hideLoading()

                            Timber.e("${result.data?.message}")

                            Toast.makeText(
                                this@SuggestionSystemCreateWizard,
                                result.data?.message,
                                Toast.LENGTH_LONG
                            ).show()

                            finish()

                            HawkUtils().removeDataCreateProposal(source)

                            Timber.d("###-- Success postSubmitCreateSs")
                        }
                        Result.Status.ERROR -> {
                            HelperLoading.hideLoading()
                            Toast.makeText(
                                this@SuggestionSystemCreateWizard,
                                result.data?.message,
                                Toast.LENGTH_LONG
                            ).show()

                            finish()

                            Timber.d("###-- Error postSubmitCreateSs")
                        }

                    }
                }
            })
        }
    }

    private fun update(data: SuggestionSystemCreateModel){
        viewModel.setPostSubmitUpdateSs(data)

        viewModel.putSubmitUpdateSs.observeEvent(this@SuggestionSystemCreateWizard) { resultObserve ->
            resultObserve.observe(this@SuggestionSystemCreateWizard, { result ->
                if (result != null) {
                    when (result.status) {
                        Result.Status.LOADING -> {
                            HelperLoading.displayLoadingWithText(this@SuggestionSystemCreateWizard,"",false)
                            Timber.d("###-- Loading putSubmitUpdateSs")
                        }
                        Result.Status.SUCCESS -> {
                            HelperLoading.hideLoading()

                            Timber.e("${result.data?.message}")

                            Toast.makeText(
                                this@SuggestionSystemCreateWizard,
                                result.data?.message,
                                Toast.LENGTH_LONG
                            ).show()

                            finish()

                            HawkUtils().removeDataCreateProposal(source)

                            Timber.d("###-- Success putSubmitUpdateSs")
                        }
                        Result.Status.ERROR -> {
                            HelperLoading.hideLoading()
                            Toast.makeText(
                                this@SuggestionSystemCreateWizard,
                                result.data?.message,
                                Toast.LENGTH_LONG
                            ).show()

                            finish()

                            Timber.d("###-- Error putSubmitUpdateSs")
                        }

                    }
                }
            })
        }
    }

    override fun onBackPressed() {
        finish()
    }

    private fun backStep(progress: Int) {
        if (progress > 1) {
            currentStep = progress-1
            currentStepCondition(currentStep)

            binding.apply {
                lytNext.visibility = VISIBLE
                actionBottom.visibility = GONE
                if (currentStep == 1) {
                    lytBack.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun bottomProgressDots(current_index: Int) {
        val currentIndex = current_index - 1
        val dotsLayout = binding.layoutDots
        val dots = arrayOfNulls<ImageView>(maxStep)

        dotsLayout.removeAllViews()
        for (i in dots.indices) {
            dots[i] = ImageView(this)
            val widthHeight = 15
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams(widthHeight, widthHeight))
            params.setMargins(10, 10, 10, 10)
            dots[i]!!.layoutParams = params
            dots[i]!!.setImageResource(R.drawable.shape_circle)
            dots[i]!!.setColorFilter(ContextCompat.getColor(this, R.color.grey_20), PorterDuff.Mode.SRC_IN)
            dotsLayout.addView(dots[i])
        }
        if (dots.isNotEmpty()) {
            dots[currentIndex]!!.setImageResource(R.drawable.shape_circle)
            dots[currentIndex]!!.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}