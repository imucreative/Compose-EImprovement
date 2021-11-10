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
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementViewModel
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
    private var maxStep = 9
    private var currentStep = 1
    private var source: String = PI_CREATE
    private lateinit var notification: HelperNotification

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
        val nik         = HawkUtils().getDataLogin().NIK
        val userId      = HawkUtils().getDataLogin().USER_ID
        val orgId       = HawkUtils().getDataLogin().ORG_ID
        val warehouseId = HawkUtils().getDataLogin().WAREHOUSE_ID
        val headId      = HawkUtils().getDataLogin().DIRECT_MANAGER_ID

        action = argsAction

        if (action == APPROVE) {
            maxStep += 1
        }

        when (argsAction) {
            EDIT, DETAIL -> {
                piNo = argsPiNo

                source = PI_DETAIL_DATA
                viewModel.setDetailPi(argsIdPi, userId)

                viewModel.getDetailPiItem.observeEvent(this) { resultObserve ->
                    resultObserve.observe(this, { result ->
                        if (result != null) {
                            when (result.status) {
                                Result.Status.LOADING -> {
                                    HelperLoading.displayLoadingWithText(this,"",false)
                                    binding.bottomNavigationBar.visibility = View.GONE

                                    Timber.d("###-- Loading getDetailPiItem")
                                }
                                Result.Status.SUCCESS -> {
                                    HelperLoading.hideLoading()
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
                                                it, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                                        },
                                        target = result.data?.data?.get(0)?.target?.let {
                                            HtmlCompat.fromHtml(
                                                it, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                                        },
                                        sebabMasalah = result.data?.data?.get(0)?.sebabMasalah,
                                        akarMasalah = result.data?.data?.get(0)?.akarMasalah,
                                        nilaiOutput = result.data?.data?.get(0)?.nilaiOutput?.let {
                                            HtmlCompat.fromHtml(
                                                it, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                                        },
                                        nqiModel = result.data?.data?.get(0)?.nqiModel,
                                        teamMember = result.data?.data?.get(0)?.teamMember,
                                        categoryFixing = result.data?.data?.get(0)?.categoryFixing,
                                        hasilImplementasi = result.data?.data?.get(0)?.implementationResult?.let {
                                            HtmlCompat.fromHtml(
                                                it, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                                        },
                                        attachment = result.data?.data?.get(0)?.attachment,
                                        statusProposal = result.data?.data?.get(0)?.statusProposal,
                                        nik = nik,
                                        headId = headId,
                                        userId = userId,
                                        orgId = orgId,
                                        warehouseId = warehouseId,
                                        source = PI_DETAIL_DATA
                                    )

                                    initToolbar(argsTitle)
                                    initComponent()
                                    Timber.d("###-- Success getDetailPiItem")
                                }
                                Result.Status.ERROR -> {
                                    HelperLoading.displayLoadingWithText(this,"",false)
                                    binding.bottomNavigationBar.visibility = View.GONE
                                    Timber.d("###-- Error getDetailPiItem")
                                }

                            }
                        }
                    })
                }
            }
            ADD -> {
                piNo = ""

                source = PI_CREATE
                HawkUtils().setTempDataCreatePi(
                    piNo = "",
                    branchCode = HawkUtils().getDataLogin().BRANCH_CODE,
                    branch = HawkUtils().getDataLogin().BRANCH,
                    department = HawkUtils().getDataLogin().DEPARTMENT,
                    subBranch = HawkUtils().getDataLogin().SUB_BRANCH,
                    source = PI_CREATE
                )

                initToolbar(argsTitle)
                initComponent()
            }
            APPROVE -> {
                piNo = argsPiNo

                source = PI_DETAIL_DATA
                viewModel.setProjectImprovementDetail(argsPiNo)
                viewModel.getProjectImprovementDetail().observe(this, { detailData ->
                    HawkUtils().setTempDataCreatePi(
                        id = detailData.id,
                        piNo = detailData.piNo,
                        department = detailData.department,
                        years = detailData.years,
                        date = detailData.date,
                        branchCode = detailData.branchCode,
                        branch = detailData.branch,
                        subBranch = detailData.subBranch,
                        title = detailData.title,
                        statusImplementationModel = detailData.statusImplementationModel,
                        identification = detailData.identification,
                        target = detailData.target,
                        sebabMasalah = detailData.sebabMasalah,
                        akarMasalah = detailData.akarMasalah,
                        nilaiOutput = detailData.nilaiOutput,
                        nqiModel = detailData.nqiModel,
                        teamMember = detailData.teamMember,
                        categoryFixing = detailData.categoryFixing,
                        hasilImplementasi = detailData.implementationResult,
                        attachment = detailData.attachment,
                        statusProposal = detailData.statusProposal,
                        source = PI_DETAIL_DATA
                    )

                    initToolbar(argsTitle)
                    initComponent()
                })
            }
        }

        Tools.setSystemBarColor(this, R.color.colorMainEImprovement, this)
        Tools.setSystemBarLight(this)
    }

    private fun initComponent() {
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
        val status = piCreateCallback.onDataPass()
        if (status) {
            if (progress < maxStep) {
                currentStep = progress + 1
                currentStepCondition(currentStep)

                binding.apply {
                    lytBack.visibility = View.VISIBLE
                    lytNext.visibility = View.VISIBLE

                    if ((action == APPROVE) || (action == DETAIL)) {
                        if (currentStep == maxStep) {
                            lytNext.visibility = View.INVISIBLE
                        }
                    }
                }
            } else {
                //CoroutineScope(Dispatchers.Default).launch {
                notification = HelperNotification()
                binding.apply {
                    notification.shownotificationyesno(
                        this@ProjectImprovementCreateWizard,
                        resources.getString(R.string.submit),
                        resources.getString(R.string.submit_desc),
                        object : HelperNotification.CallBackNotificationYesNo {
                            override fun onNotificationNo() {

                            }

                            override fun onNotificationYes() {
                                val gson = Gson()
                                val data = gson.toJson(HawkUtils().getTempDataCreatePi(source))
                                println("### Data form input : $data")
                                Toast.makeText(
                                    this@ProjectImprovementCreateWizard,
                                    resources.getString(R.string.pi_saved),
                                    Toast.LENGTH_LONG
                                ).show()
                                finish()
                            }
                        }
                    )
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