package com.fastrata.eimprovement.features.changespoint.ui.create

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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.databinding.ActivityChangesPointSystemCreateWizardBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.approval.ui.ListApprovalHistoryStatusCpFragment
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointCreateModel
import com.fastrata.eimprovement.features.changespoint.ui.ChangesPointCreateViewModel
import com.fastrata.eimprovement.ui.setToolbar
import com.fastrata.eimprovement.utils.*
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

class ChangesPointCreateWizard : AppCompatActivity(), HasSupportFragmentInjector, Injectable {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityChangesPointSystemCreateWizardBinding
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var viewModel : ChangesPointCreateViewModel
    private var maxStep = 3
    private var currentStep = 1
    private var action: String = ""
    private var cpNo: String = ""
    private var source: String = CP_CREATE
    private lateinit var notification: HelperNotification
    private val gson = Gson()
    private var data: ChangePointCreateModel? = null
    private var userId = 0
    private var orgId = 0
    private var warehouseId = 0
    private var headId = 0

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    private val args :ChangesPointCreateWizardArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangesPointSystemCreateWizardBinding.inflate(layoutInflater)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        setContentView(binding.root)

        AndroidInjection.inject(this)
        viewModel = injectViewModel(viewModelFactory)

        val argsTitle   = args.toolbarTitle!!
        val argsAction  = args.action
        val argsIdCp    = args.idCp
        val argsCpNo    = args.cpNo
        val statusProposal = args.statusProposal

        userId      = HawkUtils().getDataLogin().USER_ID
        orgId       = HawkUtils().getDataLogin().ORG_ID!!
        warehouseId = HawkUtils().getDataLogin().WAREHOUSE_ID!!
        headId      = HawkUtils().getDataLogin().DIRECT_MANAGER_ID!!

        action = argsAction

//        if (action == APPROVE) {
//            maxStep += 1
//        }

        when(argsAction) {
            EDIT, DETAIL, APPROVE, SUBMIT_PROPOSAL -> {
                cpNo = argsCpNo

                source = CP_DETAIL_DATA
                viewModel.setDetailCp(argsIdCp, userId)
                viewModel.getDetailCp.observeEvent(this) { resultObserve ->
                    resultObserve.observe(this, { result ->
                        if (result != null) {
                            when (result.status) {
                                Result.Status.LOADING -> {
                                    HelperLoading.displayLoadingWithText(this,"",false)
                                    binding.bottomNavigationBar.visibility = View.GONE

                                    Timber.d("###-- Loading get Branch")
                                }
                                Result.Status.SUCCESS -> {
                                    HelperLoading.hideLoading()
                                    binding.bottomNavigationBar.visibility = View.VISIBLE

                                    HawkUtils().setTempDataCreateCp(
                                        id = result.data?.data?.get(0)?.id,
                                        cpNo = result.data?.data?.get(0)?.cpNo,
                                        saldo = result.data?.data?.get(0)?.saldo,
                                        name = result.data?.data?.get(0)?.name,
                                        nik = result.data?.data?.get(0)?.nik,
                                        branch = result.data?.data?.get(0)?.branch,
                                        subBranch = result.data?.data?.get(0)?.subBranch,
                                        branchCode = result.data?.data?.get(0)?.branchCode,
                                        departement = result.data?.data?.get(0)?.department,
                                        position = result.data?.data?.get(0)?.position,
                                        date = result.data?.data?.get(0)?.date,
                                        keterangan = result.data?.data?.get(0)?.description,
                                        rewardData = result.data?.data?.get(0)?.reward,
                                        statusProposal = result.data?.data?.get(0)?.statusProposal,

                                        headId = result.data?.data?.get(0)?.headId,
                                        userId = result.data?.data?.get(0)?.userId,
                                        orgId = result.data?.data?.get(0)?.orgId,
                                        warehouseId = result.data?.data?.get(0)?.warehouseId,
                                        historyApproval = result.data?.data?.get(0)?.historyApproval,

                                        activityType = CP,
                                        submitType = if (argsAction == EDIT) 2 else 1,
                                        comment = result.data?.data?.get(0)?.statusProposal?.status,
                                        source = CP_DETAIL_DATA
                                    )

                                    initToolbar(argsTitle)
                                    initComponent()
                                    Timber.d("###-- Success get Branch")
                                }
                                Result.Status.ERROR -> {
                                    binding.bottomNavigationBar.visibility = View.GONE
                                    HelperLoading.hideLoading()
                                    Toast.makeText(this,"Error : ${result.message}", Toast.LENGTH_LONG).show()
                                    Timber.d("###-- Error get Branch")
                                }

                            }
                        }
                    })
                }
            }
            ADD -> {
                cpNo = ""

                source = CP_CREATE
                HawkUtils().setTempDataCreateCp(
                    id = 0,
                    cpNo = "",
                    name = HawkUtils().getDataLogin().USER_NAME,
                    nik = HawkUtils().getDataLogin().NIK,
                    branchCode = HawkUtils().getDataLogin().BRANCH_CODE,
                    branch = HawkUtils().getDataLogin().BRANCH,
                    subBranch = HawkUtils().getDataLogin().SUB_BRANCH,
                    departement = HawkUtils().getDataLogin().DEPARTMENT,
                    position = HawkUtils().getDataLogin().POSITION,
                    statusProposal = statusProposal,
                    headId = headId,
                    userId = userId,
                    orgId = orgId,
                    warehouseId = warehouseId,
                    activityType = CP,
                    submitType = 1,
                    comment = "",
                    source = CP_CREATE
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
                lytBack.visibility = View.INVISIBLE
            }

            bottomProgressDots(currentStep)

            val mFragmentManager = supportFragmentManager
            val mHomeFragment = ChangesPointStep1Fragment()
            val fragment = mFragmentManager.findFragmentByTag(ChangesPointStep1Fragment::class.java.simpleName)

            if(fragment !is ChangesPointStep1Fragment){
                val args = Bundle()
                args.putString(CP_DETAIL_DATA, cpNo)
                args.putString(ACTION_DETAIL_DATA, action)
                mHomeFragment.arguments = args
                mFragmentManager
                    .beginTransaction()
                    .add(R.id.frame_container_cp, mHomeFragment, ChangesPointStep1Fragment::class.java.simpleName)
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
                this@ChangesPointCreateWizard,
                applicationContext, color, title, description, buttonString,
                resources.getString(R.string.cancel),
                object : HelperNotification.CallBackNotificationYesNoWithComment {
                    override fun onNotificationNo() {

                    }

                    override fun onNotificationYes(comment: String) {
                        data = HawkUtils().getTempDataCreateCp(source)
                        if (comment != "") {
                            val updateProposal = ChangePointCreateModel(
                                data?.id,data?.saldo,data?.cpNo,data?.name,data?.nik,
                                userId = userId,data?.orgId,
                                data?.warehouseId,data?.headId,data?.branch,data?.subBranch, data?.department,
                                data?.position,data?.date,data?.description,data?.reward,data?.statusProposal,
                                data?.historyApproval, activityType = CP, submitType = key, comment = comment,data?.branchCode
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
                println("### step 1 = $currentStep")
                val fragment = ChangesPointStep1Fragment()
                val args = Bundle()
                args.putString(CP_DETAIL_DATA, cpNo)
                args.putString(ACTION_DETAIL_DATA, action)
                fragment.arguments = args
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_cp, fragment, ChangesPointStep1Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            2 -> {
                println("### step 2 = $currentStep")
                val fragment = ChangesPointStep2Fragment()
                val args = Bundle()
                args.putString(CP_DETAIL_DATA, cpNo)
                args.putString(ACTION_DETAIL_DATA, action)
                fragment.arguments = args
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_cp, fragment, ChangesPointStep2Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            3 -> {
                Timber.e("### step 3 = $currentStep")
                val fragment = ListApprovalHistoryStatusCpFragment()
                val args = Bundle()
                args.putString(CP_DETAIL_DATA, cpNo)
                args.putString(ACTION_DETAIL_DATA, action)
                fragment.arguments = args
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_cp, fragment, ListApprovalHistoryStatusCpFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }

    private  lateinit var cpCreateCallbac : ChangesPointCreateCallback
    fun setCpCreateCallback(cpCallback: ChangesPointCreateCallback){
        this.cpCreateCallbac = cpCallback
    }

    private fun nextStep(progress: Int) {
        data = HawkUtils().getTempDataCreateCp(source)

        val status = cpCreateCallbac.onDataPass()
        if(status){
            if (progress < maxStep){
                currentStep = progress + 1
                currentStepCondition(currentStep)

                binding.apply {
                    lytBack.visibility = View.VISIBLE
                    lytNext.visibility = View.VISIBLE

                    if (action == DETAIL) {
                        if (currentStep == maxStep) {
                            lytNext.visibility = View.INVISIBLE
                            actionBottom.visibility = View.GONE
                        }
                    } else if (action == APPROVE && data?.statusProposal?.id == 14) {
                        if (currentStep == maxStep) {
                            lytNext.visibility = View.INVISIBLE
                            actionBottom.visibility = View.VISIBLE
                        }
                    }
                }
            }else{
                val convertToJson = gson.toJson(data)

                Timber.e("### Data Form Input : $convertToJson")
                Timber.e("${data?.statusProposal}")

                var initialTypeProposal = ""
                var buttonInitialTypeProposal = ""

                if (action == SUBMIT_PROPOSAL){
                    initialTypeProposal = "Submit"
                    buttonInitialTypeProposal = "Submit"
                } else {
                    initialTypeProposal = "Save"
                    buttonInitialTypeProposal = "Save"
                }
                notification = HelperNotification()
                binding.apply {
                    notification.shownotificationyesno(
                        this@ChangesPointCreateWizard,
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
                                if (data?.cpNo.isNullOrEmpty()){
                                    Timber.e("data create Cp : $data")
                                    submit(data!!)
                                }else{
                                    Timber.e("data update Cp : $data")
                                    update(data!!)
                                }
                            }
                        }
                    )
                }

            }
        }
    }


    private fun submit(data: ChangePointCreateModel){
        viewModel.setPostSubmitCreateCp(data)

        viewModel.postSubmitCreateCp.observeEvent(this@ChangesPointCreateWizard) { resultObserve ->
            resultObserve.observe(this@ChangesPointCreateWizard, { result ->
                if (result != null) {
                    when (result.status) {
                        Result.Status.LOADING -> {
                            HelperLoading.displayLoadingWithText(this@ChangesPointCreateWizard,"",false)
                            Timber.d("###-- Loading postSubmitCreateCp")
                        }
                        Result.Status.SUCCESS -> {
                            HelperLoading.hideLoading()

                            Timber.e("${result.data?.message}")

                            Toast.makeText(
                                this@ChangesPointCreateWizard,
                                result.data?.message,
                                Toast.LENGTH_LONG
                            ).show()

                            finish()

                            HawkUtils().removeDataCreateProposal(source)

                            Timber.d("###-- Success postSubmitCreateCp")
                        }
                        Result.Status.ERROR -> {
                            HelperLoading.hideLoading()
                            Toast.makeText(
                                this@ChangesPointCreateWizard,
                                result.data?.message,
                                Toast.LENGTH_LONG
                            ).show()

                            finish()

                            Timber.d("###-- Error postSubmitCreateCp")
                        }

                    }
                }
            })
        }
    }

    private fun update(data : ChangePointCreateModel){
        viewModel.setPutSubmitUpdateCp(data)
        viewModel.putSubmitUpdateCp.observeEvent(this@ChangesPointCreateWizard) { resultObserve ->
            resultObserve.observe(this@ChangesPointCreateWizard, { result ->
                if (result != null) {
                    when (result.status) {
                        Result.Status.LOADING -> {
                            HelperLoading.displayLoadingWithText(this@ChangesPointCreateWizard,"",false)
                            Timber.d("###-- Loading putSubmitUpdateCp")
                        }
                        Result.Status.SUCCESS -> {
                            HelperLoading.hideLoading()

                            Timber.e("${result.data?.message}")

                            Toast.makeText(
                                this@ChangesPointCreateWizard,
                                result.data?.message,
                                Toast.LENGTH_LONG
                            ).show()

                            finish()

                            HawkUtils().removeDataCreateProposal(source)

                            Timber.d("###-- Success putSubmitUpdateCp")
                        }
                        Result.Status.ERROR -> {
                            HelperLoading.hideLoading()
                            Toast.makeText(
                                this@ChangesPointCreateWizard,
                                result.data?.message,
                                Toast.LENGTH_LONG
                            ).show()

                            finish()

                            Timber.d("###-- Error putSubmitUpdateCp")
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
                lytNext.visibility = View.VISIBLE
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