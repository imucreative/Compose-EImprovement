package com.fastrata.eimprovement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.fastrata.eimprovement.databinding.ActivityHomeBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.features.settings.ui.SettingsActivity
import com.fastrata.eimprovement.features.suggestionsystem.ui.SuggestionSystemActivity
import com.fastrata.eimprovement.utils.Tools
import java.util.*
import com.fastrata.eimprovement.utils.DatePickerCustom.Companion.dialogDatePicker

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var toolbarBinding: ToolbarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        setContentView(binding.root)

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
            filterActivityDate.setOnClickListener {
                dialogDatePicker(
                    context = this@HomeActivity, fragmentManager = supportFragmentManager,
                    themeDark = false, minDateIsCurrentDate = true
                )
            }

            // action menu
            menuApproval.setOnClickListener {
                Toast.makeText(
                    this@HomeActivity,
                    "Approval Selected",
                    Toast.LENGTH_LONG
                ).show()

                drawerLayout.closeDrawer(GravityCompat.START)
            }
            menuSuggestionSystem.setOnClickListener {
                Toast.makeText(
                    this@HomeActivity,
                    "Suggestion System Selected",
                    Toast.LENGTH_LONG
                ).show()

                drawerLayout.closeDrawer(GravityCompat.START)
            }
            menuProjectImprovement.setOnClickListener {
                Toast.makeText(
                    this@HomeActivity,
                    "Project Improvement Selected",
                    Toast.LENGTH_LONG
                ).show()

                drawerLayout.closeDrawer(GravityCompat.START)
            }
            menuPointExchange.setOnClickListener {
                Toast.makeText(
                    this@HomeActivity,
                    "Menu penukaran poin Selected",
                    Toast.LENGTH_LONG
                ).show()

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
