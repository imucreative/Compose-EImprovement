package com.fastrata.eimprovement.features.changesPoint.ui.create

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
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.ActivityChangesPointSystemCreateWizardBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.utils.Tools
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class ChangesPointCreateWizard : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    private lateinit var binding: ActivityChangesPointSystemCreateWizardBinding
    private lateinit var toolbarBinding: ToolbarBinding
    private val maxStep = 2
    private var currentStep = 1

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangesPointSystemCreateWizardBinding.inflate(layoutInflater)
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
            val mHomeFragment = ChangesPointStep1Fragment()
            val fragment = mFragmentManager.findFragmentByTag(ChangesPointStep1Fragment::class.java.simpleName)

            if(fragment !is ChangesPointStep1Fragment){
                mFragmentManager
                    .beginTransaction()
                    .add(R.id.frame_container_cp, mHomeFragment, ChangesPointStep1Fragment::class.java.simpleName)
                    .commit()
            }

        }
    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_black)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Changes Point"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun currentStepCondition(currentStep: Int) {
        val mFragmentManager = supportFragmentManager
        when (currentStep) {
            1 -> {
                println("### step 1 = $currentStep")
                val mCategoryFragment = ChangesPointStep1Fragment()
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_cp, mCategoryFragment, ChangesPointStep1Fragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            2 -> {
                println("### step 2 = $currentStep")
                val mCategoryFragment = ChangesPointStep2Fragment()
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container_cp, mCategoryFragment, ChangesPointStep2Fragment::class.java.simpleName)
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
                        Toast.makeText(this@ChangesPointCreateWizard, "Save Change Point", Toast.LENGTH_LONG).show()
                        backtodashboard()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }

    private fun backtodashboard() {
        /*Intent(this, HomeActivity::class.java).also {
            startActivity(it)
        }*/
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