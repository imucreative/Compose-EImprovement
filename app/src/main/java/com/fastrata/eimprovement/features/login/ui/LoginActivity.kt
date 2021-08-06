package com.fastrata.eimprovement.features.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fastrata.eimprovement.HomeActivity
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.ActivityLoginBinding
import com.fastrata.eimprovement.features.utils.Tools

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnLogin.setOnClickListener {
                Intent(this@LoginActivity, HomeActivity::class.java).also {
                    startActivity(it)
                }

            }
        }

        Tools.setSystemBarColor(this, R.color.colorMainEImprovement)
        Tools.setSystemBarLight(this)
    }
}
