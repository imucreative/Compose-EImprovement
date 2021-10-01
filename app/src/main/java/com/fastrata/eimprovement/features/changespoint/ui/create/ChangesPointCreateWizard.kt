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
import com.fastrata.eimprovement.databinding.ActivityChangesPointSystemCreateWizardBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.changespoint.ui.ChangesPointCreateCallback
import com.fastrata.eimprovement.features.changespoint.ui.ChangesPointCreateModel
import com.fastrata.eimprovement.ui.setToolbar
import com.fastrata.eimprovement.utils.*
import com.google.gson.Gson
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class ChangesPointCreateWizard : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityChangesPointSystemCreateWizardBinding
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var viewModel : ChangesPointCreateModel
    private var maxStep = 2
    private var currentStep = 1
    private var cpAction: String = ""
    private var cpNo: String = ""
    private var source: String = CP_CREATE
    private lateinit var notification: HelperNotification
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
        val argsCpNo    = args.cpNo

        cpAction = argsAction

        if (cpAction == APPROVE) {
            maxStep += 1
        }

        when(argsAction) {
            EDIT -> {
                cpNo = argsCpNo

                source = CP_DETAIL_DATA

                viewModel.setChangePointDetail(cpNo)
                viewModel.getChangePointDetail().observe(this, {detailData ->
                    println("### data :"+detailData)
                        HawkUtils().setTempDataCreateCp(
                            id = detailData.id,
                            cpno = detailData.cpNo,
                            saldo = detailData.saldo,
                            nama = detailData.name,
                            nik = detailData.nik,
                            branch = detailData.branch,
                            departement = detailData.department,
                            jabatan = detailData.job,
                            date = detailData.date,
                            keterangan = detailData.description,
                            rewarddata = detailData.reward,
                            riwayat = detailData.history,
                            source = CP_DETAIL_DATA
                        )

                    initToolbar(argsTitle)
                    initComponent()
                    })
            }
            ADD -> {
                cpNo = ""

                source = CP_CREATE
                HawkUtils().setTempDataCreateCp(
                    cpno = "",
                    branch = HawkUtils().getDataLogin().BRANCH,
                    departement = HawkUtils().getDataLogin().DEPARTMENT,
                    source = PI_CREATE
                )
                initToolbar(argsTitle)
                initComponent()
            }
            APPROVE -> {
                cpNo = argsCpNo

                source = CP_DETAIL_DATA
                viewModel.setChangePointDetail(argsCpNo)
                viewModel.getChangePointDetail().observe(this,{ detailData ->
                    println("### data :"+detailData)
                    HawkUtils().setTempDataCreateCp(
                        id = detailData.id,
                        cpno = detailData.cpNo,
                        saldo = detailData.saldo,
                        nama = detailData.name,
                        nik = detailData.nik,
                        branch = detailData.branch,
                        departement = detailData.department,
                        jabatan = detailData.job,
                        date = detailData.date,
                        keterangan = detailData.description,
                        rewarddata = detailData.reward,
                        riwayat = detailData.history,
                        source = CP_DETAIL_DATA
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
            val mHomeFragment = ChangesPointStep1Fragment()
            val fragment = mFragmentManager.findFragmentByTag(ChangesPointStep1Fragment::class.java.simpleName)

            if(fragment !is ChangesPointStep1Fragment){
                val args = Bundle()
                args.putString(CP_DETAIL_DATA, cpNo)
                args.putString(ACTION_DETAIL_DATA, cpAction)
                mHomeFragment.arguments = args
                mFragmentManager
                    .beginTransaction()
                    .add(R.id.frame_container_cp, mHomeFragment, ChangesPointStep1Fragment::class.java.simpleName)
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
                val mCategoryFragment = ChangesPointStep1Fragment()
                val args = Bundle()
                args.putString(CP_DETAIL_DATA, cpNo)
                args.putString(ACTION_DETAIL_DATA, cpAction)
                mCategoryFragment.arguments = args
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_cp, mCategoryFragment, ChangesPointStep1Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            2 -> {
                println("### step 2 = $currentStep")
                val mCategoryFragment = ChangesPointStep2Fragment()
                val args = Bundle()
                args.putString(CP_DETAIL_DATA, cpNo)
                args.putString(ACTION_DETAIL_DATA, cpAction)
                mCategoryFragment.arguments = args
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_cp, mCategoryFragment, ChangesPointStep2Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }

    private  lateinit var cpCreateCallbac : ChangesPointCreateCallback
    fun setcpCreateCallback(cpCallback: ChangesPointCreateCallback){
        cpCreateCallbac = cpCallback
    }

    private fun nextStep(progress: Int) {
        val status =cpCreateCallbac.OnDataPass()
        if(status){
            if (progress < maxStep){
                currentStep = progress + 1
                currentStepCondition(currentStep)

                binding.apply {
                    lytBack.visibility = View.VISIBLE
                    lytNext.visibility = View.VISIBLE

                    if (cpAction == APPROVE) {
                        if (currentStep == maxStep) {
                            lytNext.visibility = View.INVISIBLE
                        }
                    }
                }
            }else{
                notification = HelperNotification()
                binding.apply {
                    notification.shownotificationyesno(
                        this@ChangesPointCreateWizard,
                        "Submit",
                        "Are you sure submit this data?",
                        object : HelperNotification.CallBackNotificationYesNo {
                            override fun onNotificationNo() {

                            }

                            override fun onNotificationYes() {
                                val gson = Gson()
                                val data = gson.toJson(HawkUtils().getTempDataCreatePi(source))
                                println("### Data form input : $data")
                                Toast.makeText(
                                    this@ChangesPointCreateWizard,
                                    "Save Change Point",
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