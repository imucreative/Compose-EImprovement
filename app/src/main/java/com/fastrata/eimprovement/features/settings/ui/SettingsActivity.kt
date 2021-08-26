package com.fastrata.eimprovement.features.settings.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.ActivitySettingsBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.features.splashscreen.SplashScreenActivity
import com.fastrata.eimprovement.features.splashscreen.WelcomeMessageActivity
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.HelperNotification
import com.fastrata.eimprovement.utils.HelperNotification.CallBackNotificationYesNo
import com.fastrata.eimprovement.utils.Tools

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var notification: HelperNotification

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        setContentView(binding.root)

        initToolbar()
        initComponent()

        Tools.setSystemBarColor(this, R.color.colorMainEImprovement, this)
        Tools.setSystemBarLight(this)
    }

    private fun initComponent() {
        notification = HelperNotification()

        binding.apply {
            saldoTxt.text = HawkUtils().getSaldo()
            btnLogout.setOnClickListener {
                notification.shownotificationyesno(this@SettingsActivity,"Setting","Apakah anda yakin keluar",
                object  :CallBackNotificationYesNo {
                    override fun onNotificationNo() {

                    }

                    override fun onNotificationYes() {
                        HawkUtils().setLoginBoolean(false)
                    startActivity(Intent(this@SettingsActivity, SplashScreenActivity::class.java))
                    }
                })
            }
        }
    }



    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_black)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Setting"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
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