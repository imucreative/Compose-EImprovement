package com.fastrata.eimprovement.features.approval.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.ActivityListApprovalBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.features.approval.data.model.ApprovalModel
import com.fastrata.eimprovement.utils.DatePickerCustom
import com.fastrata.eimprovement.utils.Tools

class ListApprovalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListApprovalBinding
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var viewModelList: ListApprovalViewModel
    private lateinit var adapterList: ListApprovalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListApprovalBinding.inflate(layoutInflater)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        setContentView(binding.root)

        viewModelList = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ListApprovalViewModel::class.java)

        initToolbar()
        initComponent()

        Tools.setSystemBarColor(this, R.color.colorMainEImprovement, this)
        Tools.setSystemBarLight(this)
    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_black)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "List Approval"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initComponent() {
        viewModelList.setApproval()
        adapterList = ListApprovalAdapter()
        adapterList.notifyDataSetChanged()

        binding.apply {
            rvAppr.setHasFixedSize(true)
            rvAppr.layoutManager = LinearLayoutManager(this@ListApprovalActivity)
            rvAppr.adapter = adapterList


        }

        adapterList.setApprovalCallback(object : ListApprovalCallback {
            override fun onItemClicked(data: ApprovalModel) {
                Toast.makeText(this@ListApprovalActivity, data.ssNo, Toast.LENGTH_LONG).show()
            }
        })

        viewModelList.getApproval().observe(this, {
            if (it != null) {
                adapterList.setList(it)
            }
        })
    }

    private fun initNavigationMenu() {

        binding.apply {

            val drawer = drawerFilter

            // open drawer at start
            drawer.openDrawer(GravityCompat.END)

            filterStartDate.setOnClickListener {
                DatePickerCustom.dialogDatePicker(
                    context = this@ListApprovalActivity, fragmentManager = supportFragmentManager,
                    themeDark = false, minDateIsCurrentDate = true
                )
            }

            filterEndDate.setOnClickListener {
                DatePickerCustom.dialogDatePicker(
                    context = this@ListApprovalActivity, fragmentManager = supportFragmentManager,
                    themeDark = false, minDateIsCurrentDate = true
                )
            }

            btnCloseFilter.setOnClickListener {
                drawer.closeDrawer(GravityCompat.END)
            }

            btnApply.setOnClickListener {
                Toast.makeText(this@ListApprovalActivity,  "Apply filter", Toast.LENGTH_LONG).show()
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