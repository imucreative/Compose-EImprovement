package com.fastrata.eimprovement.features.ChangesPoint.ui.create.add

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.ActivityChangeRewardBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.utils.Tools

class AddChangeRewardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangeRewardBinding
    private lateinit var toolbarBinding: ToolbarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeRewardBinding.inflate(layoutInflater)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        setContentView(binding.root)

        initToolbar()
        initComponent()

        Tools.setSystemBarColor(this, R.color.colorMainEImprovement, this)
        Tools.setSystemBarLight(this)
    }

    private fun initComponent() {

    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_black)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Change Reward"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}