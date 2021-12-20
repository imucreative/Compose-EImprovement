package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.databinding.ActivityProjectImprovementWizardBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.approval.ui.ListApprovalHistoryStatusPiFragment
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectImprovementSystemCreateCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementViewModel
import com.fastrata.eimprovement.featuresglobal.transaction.UpdateStatusProposalPi
import com.fastrata.eimprovement.ui.setToolbar
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import com.google.gson.Gson
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

class ProjectImprovementCreateWizard : AppCompatActivity(), HasSupportFragmentInjector, Injectable {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityProjectImprovementWizardBinding
    private lateinit var toolbarBinding: ToolbarBinding
    private var piNo: String = ""
    private var action: String = ""
    private lateinit var viewModel: ProjectImprovementViewModel
    private var maxStep = 10
    private var currentStep = 1
    private var source: String = PI_CREATE
    private lateinit var notification: HelperNotification
    private val gson = Gson()
    private var data: ProjectImprovementCreateModel? = null
    private var nik : String = ""
    private var userId: Int = 0
    private var orgId: Int? = null
    private var warehouseId: Int? = null
    private var headId: Int? = null

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    private val args: ProjectImprovementCreateWizardArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProjectImprovementWizardBinding.inflate(layoutInflater)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        setContentView(binding.root)

        AndroidInjection.inject(this)
        viewModel = injectViewModel(viewModelFactory)

        val argsTitle   = args.toolbarTitle!!
        val argsAction  = args.action
        val argsIdPi    = args.idPi
        val argsPiNo    = args.piNo
        val statusProposal = args.statusProposal

        try {
            nik = HawkUtils().getDataLogin().NIK
            userId = HawkUtils().getDataLogin().USER_ID
            orgId = HawkUtils().getDataLogin().ORG_ID
            warehouseId = HawkUtils().getDataLogin().WAREHOUSE_ID
            headId = HawkUtils().getDataLogin().DIRECT_MANAGER_ID
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            Timber.d("###-- Error onCreate")
        }

        action = argsAction

//        if (action == APPROVE) {
//            maxStep += 1
//        }

        when (argsAction) {
            EDIT, DETAIL, APPROVE, SUBMIT_PROPOSAL -> {
                piNo = argsPiNo

                source = PI_DETAIL_DATA
                initToolbar(argsTitle)

                try {
                    viewModel.setDetailPi(argsIdPi, userId)

                    viewModel.getDetailPiItem.observeEvent(this) { resultObserve ->
                        resultObserve.observe(this, { result ->
                            if (result != null) {
                                when (result.status) {
                                    Result.Status.LOADING -> {
                                        HelperLoading.displayLoadingWithText(this, "", false)
                                        binding.bottomNavigationBar.visibility = View.GONE

                                        Timber.d("###-- Loading getDetailPiItem")
                                    }
                                    Result.Status.SUCCESS -> {
                                        HelperLoading.hideLoading()

                                        if (result.data?.data?.isEmpty() == true) {
                                            SnackBarCustom.snackBarIconInfo(
                                                binding.root, layoutInflater, resources, binding.root.context,
                                                result.data.message,
                                                R.drawable.ic_close, R.color.red_500
                                            )

                                            binding.noDataScreen.root.visibility = View.VISIBLE
                                        } else {
                                            binding.bottomNavigationBar.visibility = View.VISIBLE

                                            HawkUtils().setTempDataCreatePi(
                                                id = result.data?.data?.get(0)?.id,
                                                piNo = result.data?.data?.get(0)?.piNo,
                                                department = result.data?.data?.get(0)?.department,
                                                years = result.data?.data?.get(0)?.years,
                                                date = result.data?.data?.get(0)?.date,
                                                branchCode = result.data?.data?.get(0)?.branchCode,
                                                branch = result.data?.data?.get(0)?.branch,
                                                subBranch = result.data?.data?.get(0)?.subBranch,
                                                title = result.data?.data?.get(0)?.title,
                                                statusImplementationModel = result.data?.data?.get(0)?.statusImplementationModel,
                                                identification = result.data?.data?.get(0)?.identification?.let {
                                                    HtmlCompat.fromHtml(
                                                        it, HtmlCompat.FROM_HTML_MODE_LEGACY
                                                    ).toString()
                                                },
                                                target = result.data?.data?.get(0)?.target?.let {
                                                    HtmlCompat.fromHtml(
                                                        it, HtmlCompat.FROM_HTML_MODE_LEGACY
                                                    ).toString()
                                                },
                                                sebabMasalah = result.data?.data?.get(0)?.sebabMasalah,
                                                akarMasalah = result.data?.data?.get(0)?.akarMasalah,
                                                nilaiOutput = result.data?.data?.get(0)?.nilaiOutput?.let {
                                                    HtmlCompat.fromHtml(
                                                        it, HtmlCompat.FROM_HTML_MODE_LEGACY
                                                    ).toString()
                                                },
                                                nqiModel = result.data?.data?.get(0)?.nqiModel,
                                                teamMember = result.data?.data?.get(0)?.teamMember,
                                                categoryFixing = result.data?.data?.get(0)?.categoryFixing,
                                                hasilImplementasi = result.data?.data?.get(0)?.implementationResult?.let {
                                                    HtmlCompat.fromHtml(
                                                        it, HtmlCompat.FROM_HTML_MODE_LEGACY
                                                    ).toString()
                                                },
                                                attachment = result.data?.data?.get(0)?.attachment,
                                                statusProposal = result.data?.data?.get(0)?.statusProposal,
                                                nik = result.data?.data?.get(0)?.nik,
                                                headId = result.data?.data?.get(0)?.headId,
                                                directMgrNik = result.data?.data?.get(0)?.directMgrNik,
                                                directMgr = result.data?.data?.get(0)?.directMgr,
                                                userId = result.data?.data?.get(0)?.userId,
                                                orgId = result.data?.data?.get(0)?.orgId,
                                                warehouseId = result.data?.data?.get(0)?.warehouseId,
                                                historyApproval = result.data?.data?.get(0)?.historyApproval,

                                                activityType = PI,
                                                submitType = if (argsAction == EDIT) 2 else 1,
                                                comment = "",
                                                source = PI_DETAIL_DATA
                                            )

                                            initComponent()
                                            Timber.d("###-- Success getDetailPiItem")
                                        }
                                    }
                                    Result.Status.ERROR -> {
                                        binding.bottomNavigationBar.visibility = View.GONE
                                        binding.noDataScreen.root.visibility = View.VISIBLE
                                        HelperLoading.hideLoading()

                                        Toast.makeText(
                                            this,
                                            "Error : ${result.message}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        Timber.d("###-- Error getDetailPiItem")
                                    }

                                }
                            }
                        })
                    }
                } catch (e: Exception) {
                    binding.bottomNavigationBar.visibility = View.GONE
                    binding.noDataScreen.root.visibility = View.VISIBLE

                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                    Timber.d("###-- Error onCreate")
                }
            }
            ADD -> {
                piNo = ""

                source = PI_CREATE
                HawkUtils().setTempDataCreatePi(
                    id = 0,
                    piNo = "",
                    nik = HawkUtils().getDataLogin().NIK,
                    branchCode = HawkUtils().getDataLogin().BRANCH_CODE,
                    branch = HawkUtils().getDataLogin().BRANCH,
                    subBranch = HawkUtils().getDataLogin().SUB_BRANCH,
                    department = HawkUtils().getDataLogin().DEPARTMENT,
                    statusProposal = statusProposal,
                    headId = headId,
                    directMgr = HawkUtils().getDataLogin().DIRECT_MANAGER,
                    directMgrNik = HawkUtils().getDataLogin().DIRECT_MANAGER_NIK,
                    userId = userId,
                    orgId = orgId,
                    warehouseId = warehouseId,
                    activityType = PI,
                    submitType = 1,
                    comment = "",
                    source = PI_CREATE
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
            val mHomeFragment = ProjectImprovStep1Fragment()
            val fragment = mFragmentManager.findFragmentByTag(ProjectImprovStep1Fragment::class.java.simpleName)

            if(fragment !is ProjectImprovStep1Fragment){
                val args = Bundle()
                args.putString(PI_DETAIL_DATA, piNo)
                args.putString(ACTION_DETAIL_DATA, action)
                mHomeFragment.arguments = args
                mFragmentManager
                    .beginTransaction()
                    .add(R.id.frame_container_pi, mHomeFragment, ProjectImprovStep1Fragment::class.java.simpleName)
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
        val data = HawkUtils().getTempDataCreatePi(source)
        Timber.e("Stat Props: ${data?.statusProposal?.id}")

        binding.apply {
            notification.showNotificationYesNoWithRating(
                this@ProjectImprovementCreateWizard,
                applicationContext, color, title, description, buttonString,
                resources.getString(R.string.cancel), data?.statusProposal?.id, key,
                object : HelperNotification.CallBackNotificationYesNoWithRating {
                    override fun onNotificationNo() {

                    }

                    override fun onNotificationYes(comment: String, rate: Int) {
                        val score = if (key == 1) rate else 0

                        if (comment != "") {
                            val updateProposal = ProjectImprovementCreateModel(
                                data?.id,data?.piNo,
                                userId = userId,
                                data?.nik,data?.orgId, data?.warehouseId,data?.headId,directMgr = data?.directMgr,directMgrNik = data?.directMgrNik,data?.department,
                                data?.years, data?.date,data?.branchCode,data?.branch,data?.subBranch,
                                data?.title,data?.statusImplementationModel,data?.identification,
                                data?.target,data?.sebabMasalah,data?.akarMasalah,data?.nilaiOutput,
                                data?.nqiModel,data?.teamMember,data?.categoryFixing,data?.implementationResult,
                                data?.attachment,data?.statusProposal,historyApproval = data?.historyApproval, score = score,
                                activityType = PI,submitType = key, comment = comment
                            )

                            UpdateStatusProposalPi(
                                viewModel,
                                context = applicationContext,
                                owner = this@ProjectImprovementCreateWizard
                            ).updatePi(
                                data = updateProposal,
                                context = applicationContext,
                                owner = this@ProjectImprovementCreateWizard
                            ) {
                                if(it){
                                    finish()
                                    HawkUtils().removeDataCreateProposal(source)
                                }
                            }
                        } else {
                            SnackBarCustom.snackBarIconInfo(
                                binding.root, layoutInflater, resources, root.context,
                                resources.getString(R.string.wrong_field),
                                R.drawable.ic_close, R.color.red_500
                            )
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
                val fragment = ProjectImprovStep1Fragment()
                val args = Bundle()
                args.putString(PI_DETAIL_DATA, piNo)
                args.putString(ACTION_DETAIL_DATA, action)
                fragment.arguments = args
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_pi, fragment,ProjectImprovStep1Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            2 -> {
                println("### step 2 = $currentStep")
                val fragment = ProjectImprovStep2Fragment()
                val args = Bundle()
                args.putString(PI_DETAIL_DATA, piNo)
                args.putString(ACTION_DETAIL_DATA, action)
                fragment.arguments = args
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_pi, fragment,ProjectImprovStep2Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            3 -> {
                println("### step 3 = $currentStep")
                val fragment = ProjectImprovStep3Fragment()
                val args = Bundle()
                args.putString(PI_DETAIL_DATA, piNo)
                args.putString(ACTION_DETAIL_DATA, action)
                fragment.arguments = args
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_pi, fragment,ProjectImprovStep3Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            4 -> {
                println("### step 4 = $currentStep")
                val fragment = ProjectImprovStep4Fragment()
                val args = Bundle()
                args.putString(PI_DETAIL_DATA, piNo)
                args.putString(ACTION_DETAIL_DATA, action)
                fragment.arguments = args
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_pi, fragment,ProjectImprovStep4Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            5 -> {
                println("### step 5 = $currentStep")
                val fragment = ProjectImprovStep5Fragment()
                val args = Bundle()
                args.putString(PI_DETAIL_DATA, piNo)
                args.putString(ACTION_DETAIL_DATA, action)
                fragment.arguments = args
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_pi, fragment,ProjectImprovStep5Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            6 -> {
                println("### step 6 = $currentStep")
                val fragment = ProjectImprovStep6Fragment()
                val args = Bundle()
                args.putString(PI_DETAIL_DATA, piNo)
                args.putString(ACTION_DETAIL_DATA, action)
                fragment.arguments = args
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_pi, fragment,ProjectImprovStep6Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            7 -> {
                println("### step 7 = $currentStep")
                val fragment = ProjectImprovStep7Fragment()
                val args = Bundle()
                args.putString(PI_DETAIL_DATA, piNo)
                args.putString(ACTION_DETAIL_DATA, action)
                fragment.arguments = args
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_pi, fragment,ProjectImprovStep7Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            8 -> {
                println("### step 8 = $currentStep")
                val fragment = ProjectImprovStep8Fragment()
                val args = Bundle()
                args.putString(PI_DETAIL_DATA, piNo)
                args.putString(ACTION_DETAIL_DATA, action)
                fragment.arguments = args
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_pi, fragment,ProjectImprovStep8Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            9 -> {
                println("### step 9 = $currentStep")
                val fragment = ProjectImprovStep9Fragment()
                val args = Bundle()
                args.putString(PI_DETAIL_DATA, piNo)
                args.putString(ACTION_DETAIL_DATA, action)
                fragment.arguments = args
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_pi, fragment,ProjectImprovStep9Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            10 -> {
                Timber.e("### step 10 = $currentStep")
                val fragment = ListApprovalHistoryStatusPiFragment()
                val args = Bundle()
                args.putString(PI_DETAIL_DATA, piNo)
                args.putString(ACTION_DETAIL_DATA, action)
                fragment.arguments = args
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_pi, fragment, ListApprovalHistoryStatusPiFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
        }

    }

    private lateinit var piCreateCallback : ProjectImprovementSystemCreateCallback
    fun setPiCreateCallback(piCreateCallback: ProjectImprovementSystemCreateCallback){
        this.piCreateCallback = piCreateCallback
    }

    private fun nextStep(progress: Int) {
        data = HawkUtils().getTempDataCreatePi(source)

        val status = piCreateCallback.onDataPass()
        if (status) {
            if (progress < maxStep) {
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
                    } else if (action == APPROVE && (data?.statusProposal?.id == 3 || data?.statusProposal?.id == 8)) {

                        if (currentStep == maxStep) {
                            lytNext.visibility = View.INVISIBLE
                            actionBottom.visibility = View.VISIBLE
                        }
                    }
                }
            } else {
                val convertToJson = gson.toJson(data)

                Timber.e("### Data form input: $convertToJson")
                Timber.e("### Data Proposal : ${data?.statusProposal}")

                var initialTypeProposal = ""
                var buttonInitialTypeProposal = ""

                when (data?.statusProposal?.id) {
                    1, 11, 4 -> {
                        when (action) {
                            EDIT, ADD -> {
                                initialTypeProposal = "Save"
                                buttonInitialTypeProposal = "Save"
                            }
                            else -> {
                                initialTypeProposal = "Submit"
                                buttonInitialTypeProposal = "Send"
                            }
                        }
                    }
                    5, 9 ->{
                        initialTypeProposal = "Submit Laporan Akhir"
                        buttonInitialTypeProposal = "Send"
                    }
                    8 -> {
                        initialTypeProposal = "Review"
                        buttonInitialTypeProposal = "Review"
                    }
                }

                notification = HelperNotification()
                binding.apply {
                    if (data?.piNo.isNullOrEmpty()) {
                        notification.showNotificationYesNoSubmit(
                            this@ProjectImprovementCreateWizard,
                            applicationContext,
                            R.color.blue_500,
                            initialTypeProposal,
                            resources.getString(R.string.submit_desc),
                            buttonInitialTypeProposal,
                            resources.getString(R.string.submit),
                            resources.getString(R.string.cancel),
                            object : HelperNotification.CallBackNotificationYesNoSubmit {
                                override fun onNotificationNo() {

                                }

                                override fun onNotificationYes() {
                                    UpdateStatusProposalPi(
                                        viewModel,
                                        context = applicationContext,
                                        owner = this@ProjectImprovementCreateWizard
                                    ).submitPi(
                                        data = data!!,
                                        action = "save",
                                        context = applicationContext,
                                        owner = this@ProjectImprovementCreateWizard
                                    ) {
                                        if (it) {
                                            finish()
                                            HawkUtils().removeDataCreateProposal(source)
                                        }
                                    }
                                }

                                override fun onNotificationSubmit() {
                                    if (data?.statusProposal?.id == 1) {
                                        UpdateStatusProposalPi(
                                            viewModel,
                                            context = applicationContext,
                                            owner = this@ProjectImprovementCreateWizard
                                        ).submitPi(
                                            data = data!!,
                                            action = "submit",
                                            context = applicationContext,
                                            owner = this@ProjectImprovementCreateWizard
                                        ) {
                                            if (it) {
                                                finish()
                                                HawkUtils().removeDataCreateProposal(source)
                                            }
                                        }
                                    } else {
                                        SnackBarCustom.snackBarIconInfo(
                                            root, layoutInflater, resources, root.context,
                                            resources.getString(R.string.title_past_period),
                                            R.drawable.ic_close, R.color.red_500
                                        )
                                    }
                                }
                            }
                        )
                    } else {
                        notification.showNotificationYesNo(
                            this@ProjectImprovementCreateWizard,
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
                                    UpdateStatusProposalPi(
                                        viewModel,
                                        context = applicationContext,
                                        owner = this@ProjectImprovementCreateWizard
                                    ).updatePi(
                                        data = data!!,
                                        context = applicationContext,
                                        owner = this@ProjectImprovementCreateWizard
                                    ) {
                                        if (it) {
                                            finish()
                                            HawkUtils().removeDataCreateProposal(source)
                                        }
                                    }
                                }
                            }
                        )
                    }
                }

            }
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
                actionBottom.visibility = View.GONE
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