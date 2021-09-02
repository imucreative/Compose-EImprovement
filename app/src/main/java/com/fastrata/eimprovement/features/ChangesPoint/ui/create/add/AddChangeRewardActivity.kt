package com.fastrata.eimprovement.features.ChangesPoint.ui.create.add

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fastrata.eimprovement.HomeActivity
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.ActivityChangeRewardBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.features.ChangesPoint.ui.create.ChangesPointStep2Fragment
import com.fastrata.eimprovement.utils.Tools

class AddChangeRewardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangeRewardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeRewardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponent()

        Tools.setSystemBarColor(this, R.color.colorMainEImprovement, this)
        Tools.setSystemBarLight(this)
    }

    private fun initComponent() {
        binding.apply {
            addReward.setOnClickListener {
                Intent(this@AddChangeRewardActivity, HomeActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }

}