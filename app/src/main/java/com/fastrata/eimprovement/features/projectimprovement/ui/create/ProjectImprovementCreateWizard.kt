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
import androidx.fragment.app.Fragment
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.ActivityProjectImprovementWizardBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectImprovementSystemCreateCallback
import com.fastrata.eimprovement.ui.setToolbar
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import com.google.gson.Gson
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class ProjectImprovementCreateWizard : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    private lateinit var binding: ActivityProjectImprovementWizardBinding
    private lateinit var toolbarBinding: ToolbarBinding
    private val maxStep = 9
    private var currentStep = 1
    private var source: String = PI_CREATE
    private lateinit var notification: HelperNotification

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProjectImprovementWizardBinding.inflate(layoutInflater)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        setContentView(binding.root)

        HawkUtils().setTempDataCreatePi(
            piNo = "",
            branch = HawkUtils().getDataLogin().BRANCH,
            department = HawkUtils().getDataLogin().DEPARTMENT,
            subBranch = HawkUtils().getDataLogin().SUB_BRANCH
        )

        initToolbar("Create Project Improvement")
        initComponent()

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

    private lateinit var piCreateCallback : ProjectImprovementSystemCreateCallback
    fun setPiCreateCallback(piCreateCallback: ProjectImprovementSystemCreateCallback){
        this.piCreateCallback = piCreateCallback
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
                lytSave.visibility = View.GONE
                if (currentStep == 1) {
                    lytBack.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun currentStepCondition(currentStep: Int) {
        val mFragmentManager = supportFragmentManager
        when (currentStep) {
            1 -> {
                println("### step 1 = $currentStep")
                val mCategoryFragment = ProjectImprovStep1Fragment()
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_pi, mCategoryFragment,ProjectImprovStep1Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            2 -> {
                println("### step 2 = $currentStep")
                val mCategoryFragment = ProjectImprovStep2Fragment()
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_pi, mCategoryFragment,ProjectImprovStep2Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            3 -> {
                println("### step 3 = $currentStep")
                val mCategoryFragment = ProjectImprovStep3Fragment()
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_pi, mCategoryFragment,ProjectImprovStep3Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            4 -> {
                println("### step 4 = $currentStep")
                val mCategoryFragment = ProjectImprovStep4Fragment()
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_pi, mCategoryFragment,ProjectImprovStep4Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            5 -> {
                println("### step 5 = $currentStep")
                val mCategoryFragment = ProjectImprovStep5Fragment()
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_pi, mCategoryFragment,ProjectImprovStep5Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            6 -> {
                println("### step 6 = $currentStep")
                val mCategoryFragment = ProjectImprovStep6Fragment()
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_pi, mCategoryFragment,ProjectImprovStep6Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            7 -> {
                println("### step 7 = $currentStep")
                val mCategoryFragment = ProjectImprovStep7Fragment()
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_pi, mCategoryFragment,ProjectImprovStep7Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            8 -> {
                println("### step 8 = $currentStep")
                val mCategoryFragment = ProjectImprovStep8Fragment()
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_pi, mCategoryFragment,ProjectImprovStep8Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            9 -> {
                println("### step 9 = $currentStep")
                val mCategoryFragment = ProjectImprovStep9Fragment()
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_pi, mCategoryFragment,ProjectImprovStep8Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
        }

    }

    private fun nextStep(progress: Int) {
        if(progress <maxStep) {
            val status = piCreateCallback.onDataPass()
            if(status) {
                currentStep = progress + 1
                currentStepCondition(currentStep)

                binding.apply {
                    lytBack.visibility = View.VISIBLE
                    lytNext.visibility = View.VISIBLE
                    lytSave.visibility = View.GONE
                    if (currentStep == maxStep) {
                        lytNext.visibility = View.GONE
                        lytSave.visibility = View.VISIBLE
                        lytSave.setOnClickListener {
                            val gson = Gson()
                            val data = gson.toJson(HawkUtils().getTempDataCreatePi())
                            println("### Data form input : $data")
                            Toast.makeText(
                                this@ProjectImprovementCreateWizard,
                                "Save Project Improvement",
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        }
                    }
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