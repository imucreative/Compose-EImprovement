package com.fastrata.eimprovement.features.suggestionsystem.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.ActivitySuggestionSystemBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemModel
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SuggestionSystemCreateWizard
import com.fastrata.eimprovement.utils.DatePickerCustom
import com.fastrata.eimprovement.utils.Tools

class SuggestionSystemActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuggestionSystemBinding
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var viewModel: SuggestionSystemViewModel
    private lateinit var adapter: SuggestionSystemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySuggestionSystemBinding.inflate(layoutInflater)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SuggestionSystemViewModel::class.java)

        initToolbar()
        initComponent()

        Tools.setSystemBarColor(this, R.color.colorMainEImprovement, this)
        Tools.setSystemBarLight(this)
    }

    private fun initComponent() {
        viewModel.setSuggestionSystem()
        adapter = SuggestionSystemAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvSs.setHasFixedSize(true)
            rvSs.layoutManager = LinearLayoutManager(this@SuggestionSystemActivity)
            rvSs.adapter = adapter

            createSs.setOnClickListener {
                Intent(this@SuggestionSystemActivity, SuggestionSystemCreateWizard::class.java).also {
                    startActivity(it)
                }
            }
        }

        adapter.setSuggestionSystemCallback(object : SuggestionSystemCallback{
            override fun onItemClicked(data: SuggestionSystemModel) {
                Toast.makeText(this@SuggestionSystemActivity, data.ssNo, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.getSuggestionSystem().observe(this, {
            if (it != null) {
                adapter.setList(it)
            }
        })
    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_black)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Suggestion System (SS)"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initNavigationMenu() {

        binding.apply {

            val drawer = drawerFilter

            // open drawer at start
            drawer.openDrawer(GravityCompat.END)

            filterStartDate.setOnClickListener {
                DatePickerCustom.dialogDatePicker(
                    context = this@SuggestionSystemActivity, fragmentManager = supportFragmentManager,
                    themeDark = false, minDateIsCurrentDate = true
                )
            }

            filterEndDate.setOnClickListener {
                DatePickerCustom.dialogDatePicker(
                    context = this@SuggestionSystemActivity, fragmentManager = supportFragmentManager,
                    themeDark = false, minDateIsCurrentDate = true
                )
            }

            btnCloseFilter.setOnClickListener {
                drawer.closeDrawer(GravityCompat.END)
            }

            btnApply.setOnClickListener {
                Toast.makeText(this@SuggestionSystemActivity,  "Apply filter", Toast.LENGTH_LONG).show()
                drawer.closeDrawer(GravityCompat.END)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filter_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
            R.id.filter_menu -> {
                initNavigationMenu()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}