package com.fastrata.eimprovement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.fastrata.eimprovement.databinding.ActivityHomeBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.features.ChangesPoint.ui.ChangesPointActivity
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementActivity
import com.fastrata.eimprovement.features.settings.ui.SettingsActivity
import com.fastrata.eimprovement.features.suggestionsystem.ui.SuggestionSystemActivity
import com.fastrata.eimprovement.utils.DatePickerCustom
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.HelperNotification
import com.fastrata.eimprovement.utils.Tools
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var notification: HelperNotification
    private lateinit var datePicker: DatePickerCustom

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        setContentView(binding.root)
        notification = HelperNotification()

        datePicker = DatePickerCustom(
            context = binding.root.context, themeDark = true,
            minDateIsCurrentDate = true, supportFragmentManager
        )

        initToolbar()
        initComponent()

        Tools.setSystemBarColor(this, R.color.colorMainEImprovement, this)
        Tools.setSystemBarLight(this)
    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_menu)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initComponent() {
        binding.apply {
            linearSaldo.setOnClickListener {
                notification.showNotification(this@HomeActivity,"SALOD ANDA",HawkUtils().getSaldo())
            }
            (saldoTxt as TextView).text = HawkUtils().getSaldo()
            filterActivityDate.setOnClickListener {
                datePicker.showDialog(object : DatePickerCustom.Callback {
                    override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                        val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                        val mon = month + 1
                        val monthStr = if (mon < 10) "0$mon" else "$mon"

                        Toast.makeText(this@HomeActivity, "$dayStr-$monthStr-$year", Toast.LENGTH_LONG).show()
                    }
                })
            }

            // action menu
            menuApproval.setOnClickListener {
//                Intent(this@HomeActivity, ListApprovalActivity::class.java).also {
//                    startActivity(it)
//                }
                notification.showErrorDialog(this@HomeActivity,"Peringatan","berhasil ga")

                drawerLayout.closeDrawer(GravityCompat.START)
            }
            menuSuggestionSystem.setOnClickListener {
                Intent(this@HomeActivity, SuggestionSystemActivity::class.java).also {
                    startActivity(it)
                }

                drawerLayout.closeDrawer(GravityCompat.START)
            }
            menuProjectImprovement.setOnClickListener {
               Intent(this@HomeActivity,ProjectImprovementActivity::class.java).also {
                   startActivity(it)
               }
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            menuPointExchange.setOnClickListener {
                Intent(this@HomeActivity,ChangesPointActivity::class.java).also {
                    startActivity(it)
                }
                drawerLayout.closeDrawer(GravityCompat.START)

                drawerLayout.closeDrawer(GravityCompat.START)
            }

        }
    }

    private fun initNavigationMenu() {
        val drawer = binding.drawerLayout

        val toggle = object : ActionBarDrawerToggle(
            this@HomeActivity,
            drawer,
            toolbarBinding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }
        }
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        // open drawer at start
        drawer.openDrawer(GravityCompat.START)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                initNavigationMenu()
            }
            R.id.setting_menu -> {
                Intent(this, SettingsActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
