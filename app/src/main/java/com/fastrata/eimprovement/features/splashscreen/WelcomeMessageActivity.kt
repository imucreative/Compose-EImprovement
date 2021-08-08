package com.fastrata.eimprovement.features.splashscreen

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.ActivityWelcomeMessageBinding
import com.fastrata.eimprovement.features.login.ui.LoginActivity
import com.fastrata.eimprovement.utils.PreferenceUtils
import com.fastrata.eimprovement.utils.Tools

class WelcomeMessageActivity : AppCompatActivity() {

    private val _maxStep = 4

    private lateinit var myViewPagerAdapter: WelcomeMessageAdapter

    private lateinit var welcomeMessageModel: WelcomeMessageModel
    private lateinit var binding: ActivityWelcomeMessageBinding

    private lateinit var viewPager: ViewPager
    private lateinit var btnNext: Button
    private lateinit var dotsLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.viewPager
        btnNext = binding.btnNext
        dotsLayout = binding.layoutDots

        welcomeMessageModel = WelcomeMessageModel()

        // adding startup bottom dots
        bottomProgressDots(0)

        myViewPagerAdapter = WelcomeMessageAdapter(layoutInflater)

        viewPager.adapter = myViewPagerAdapter
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener)
        viewPager.clipToPadding = false
        viewPager.setPadding(0, 0, 0, 0)
        viewPager.pageMargin = resources.getDimensionPixelOffset(R.dimen.viewpager_margin_overlap)
        viewPager.offscreenPageLimit = 4
        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (viewPager.currentItem == myViewPagerAdapter.aboutTitleArray.size - 1) {
                    btnNext.text = resources.getString(R.string.getStarted)
                } else {
                    btnNext.text = resources.getString(R.string.next)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        btnNext.setOnClickListener {
            val current = viewPager.currentItem + 1
            if (current < _maxStep) {
                // move to next screen
                viewPager.currentItem = current
                welcomeMessageIsDisplay(false)
            } else {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()
                welcomeMessageIsDisplay(true)
            }
        }

        Tools.setSystemBarColor(this, R.color.colorMainEImprovement, this)
        Tools.setSystemBarLight(this)
    }

    private fun welcomeMessageIsDisplay(isDisplayWelcomeMessage: Boolean) {
        val preferenceUtils = PreferenceUtils(this)
        welcomeMessageModel.isDisplay = isDisplayWelcomeMessage
        preferenceUtils.setWelcomeMessage(welcomeMessageModel)
    }

    // viewpager change listener
    private val viewPagerPageChangeListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            bottomProgressDots(position)
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
        override fun onPageScrollStateChanged(arg0: Int) {}
    }

    private fun bottomProgressDots(current_index: Int) {
        val dots = arrayOfNulls<ImageView>(_maxStep)
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
            dots[current_index]!!.setImageResource(R.drawable.shape_circle)
            dots[current_index]!!.setColorFilter(
                ContextCompat.getColor(this, R.color.colorPrimaryDark),
                PorterDuff.Mode.SRC_IN
            )
        }
    }

}
