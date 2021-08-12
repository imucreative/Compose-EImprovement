package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.ActivityProjectImprovementWizardBinding
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep1Binding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SuggestionSystemStep1Fragment
import com.fastrata.eimprovement.utils.Tools

class ProjectImprovementCreateWizard : AppCompatActivity() {

    private lateinit var binding: ActivityProjectImprovementWizardBinding
    private lateinit var toolbarBinding: ToolbarBinding
    private val maxStep = 4
    private var currentStep = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProjectImprovementWizardBinding.inflate(layoutInflater)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        setContentView(binding.root)

        initToolbar()
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
                    .add(R.id.frame_container, mHomeFragment, ProjectImprovStep1Fragment::class.java.simpleName)
                    .commit()
            }

        }
    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_black)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Create Project Improvement (PI)"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        finish()
    }

    private fun backStep(progress: Int) {
        if (progress > 1) {
            currentStep = progress-1;
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
                    replace(R.id.frame_container, mCategoryFragment,ProjectImprovStep1Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            2 -> {
                println("### step 2 = $currentStep")
                val mCategoryFragment = ProjectImprovStep2Fragment()
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, mCategoryFragment,ProjectImprovStep2Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            3 -> {
                println("### step 3 = $currentStep")
                val mCategoryFragment = ProjectImprovStep3Fragment()
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, mCategoryFragment,ProjectImprovStep3Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            4 -> {
                println("### step 4 = $currentStep")
                val mCategoryFragment = ProjectImprovStep4Fragment()
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, mCategoryFragment,ProjectImprovStep4Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            5 -> {
                println("### step 5 = $currentStep")
                val mCategoryFragment = ProjectImprovStep5Fragment()
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, mCategoryFragment,ProjectImprovStep5Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            6 -> {
                println("### step 6 = $currentStep")
                val mCategoryFragment = ProjectImprovStep6Fragment()
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, mCategoryFragment,ProjectImprovStep6Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            7 -> {
                println("### step 7 = $currentStep")
                val mCategoryFragment = ProjectImprovStep7Fragment()
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, mCategoryFragment,ProjectImprovStep7Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            8 -> {
                println("### step 8 = $currentStep")
                val mCategoryFragment = ProjectImprovStep8Fragment()
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, mCategoryFragment,ProjectImprovStep8Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
        }

    }

    private fun nextStep(progress: Int) {
        if (progress < maxStep) {
            currentStep = progress+1
            currentStepCondition(currentStep)

            binding.apply {
                lytBack.visibility = View.VISIBLE
                lytNext.visibility = View.VISIBLE
                lytSave.visibility = View.GONE
                if (currentStep == maxStep) {
                    lytNext.visibility = View.GONE
                    lytSave.visibility = View.VISIBLE
                    lytSave.setOnClickListener {
                        Toast.makeText(this@ProjectImprovementCreateWizard, "Save suggestion system", Toast.LENGTH_LONG).show()
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


}