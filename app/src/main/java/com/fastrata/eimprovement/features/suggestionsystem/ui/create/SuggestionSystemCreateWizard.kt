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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.ActivitySuggestionSystemCreateWizardBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.approval.ui.ListApprovalHistoryStatusSsFragment
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
    private var maxStep = 4
    private var currentStep = 1
    private var source: String = SS_CREATE
    private lateinit var notification: HelperNotification

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
        val argsSsNo    = args.ssNo

        ssAction = argsAction

        if (ssAction == APPROVE) {
            maxStep += 1
        }

        when (argsAction) {
            EDIT -> {
                ssNo = argsSsNo

                source = SS_DETAIL_DATA
                viewModel.setSuggestionSystemDetail(argsSsNo)
                viewModel.getSuggestionSystemDetail().observe(this, { detailData ->
                    Timber.e("### ambil dari getSuggestionSystemDetail $detailData")
                    HawkUtils().setTempDataCreateSs(
                        ssNo = detailData.ssNo,
                        title = detailData.title,
                        listCategory = detailData.categoryImprovement,
                        name = detailData.name,
                        nik = detailData.nik,
                        branch = detailData.branch,
                        department = detailData.department,
                        directMgr = detailData.directMgr,
                        suggestion = detailData.suggestion,
                        problem = detailData.problem,
                        statusImplementation = detailData.statusImplementation,
                        teamMember = detailData.teamMember,
                        attachment = detailData.attachment,
                        statusProposal = detailData.statusProposal,
                        source = SS_DETAIL_DATA
                    )

                    initToolbar(argsTitle)
                    initComponent()
                })
            }
            ADD -> {
                ssNo = ""

                source = SS_CREATE
                // ini ambil dari session
                HawkUtils().setTempDataCreateSs(
                    ssNo = "",
                    name = HawkUtils().getDataLogin().USER_NAME,
                    nik = HawkUtils().getDataLogin().NIK,
                    branch = HawkUtils().getDataLogin().BRANCH,
                    department = HawkUtils().getDataLogin().DEPARTMENT,
                    directMgr = HawkUtils().getDataLogin().DIRECT_MANAGER,
                    source = SS_CREATE
                )

                initToolbar(argsTitle)
                initComponent()
            }
            APPROVE -> {
                ssNo = argsSsNo

                source = SS_DETAIL_DATA
                viewModel.setSuggestionSystemDetail(argsSsNo)
                viewModel.getSuggestionSystemDetail().observe(this, { detailData ->
                    Timber.e("### ambil dari getSuggestionSystemDetail $detailData")
                    HawkUtils().setTempDataCreateSs(
                        ssNo = detailData.ssNo,
                        title = detailData.title,
                        listCategory = detailData.categoryImprovement,
                        name = detailData.name,
                        nik = detailData.nik,
                        branch = detailData.branch,
                        department = detailData.department,
                        directMgr = detailData.directMgr,
                        suggestion = detailData.suggestion,
                        problem = detailData.problem,
                        statusImplementation = detailData.statusImplementation,
                        teamMember = detailData.teamMember,
                        attachment = detailData.attachment,
                        statusProposal = detailData.statusProposal,
                        source = SS_DETAIL_DATA
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
            4 -> {
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
                    lytBack.visibility = View.VISIBLE
                    lytNext.visibility = View.VISIBLE

                    if (ssAction == APPROVE) {
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
                        this@SuggestionSystemCreateWizard,
                        "Submit",
                        "Are you sure submit this data?",
                        object : HelperNotification.CallBackNotificationYesNo {
                            override fun onNotificationNo() {

                            }

                            override fun onNotificationYes() {
                                val gson = Gson()
                                val data = gson.toJson(HawkUtils().getTempDataCreateSs(source))
                                println("### Data form input : $data")
                                Toast.makeText(
                                    this@SuggestionSystemCreateWizard,
                                    "Save suggestion system",
                                    Toast.LENGTH_LONG
                                ).show()
                                finish()
                            }
                        }
                    )
                }
                // }
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