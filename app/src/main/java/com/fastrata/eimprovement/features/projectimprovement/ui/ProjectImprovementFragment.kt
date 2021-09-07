package com.fastrata.eimprovement.features.projectimprovement.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.projectimprovement.adapter.ProjectImprovementAdapter
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectSystemCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementItem
import com.fastrata.eimprovement.ui.setToolbar
import javax.inject.Inject

class ProjectImprovementFragment : Fragment(), Injectable{
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentProjectImprovementBinding
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var viewModel : ProjectImprovementViewModel
    private lateinit var adapter : ProjectImprovementAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProjectImprovementBinding.inflate(inflater, container, false)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        context ?: return binding.root

        viewModel = injectViewModel(viewModelFactory)

        setHasOptionsMenu(true);

        initToolbar()
        initComponent(requireActivity())

        return binding.root
    }

    private fun initComponent(activity: FragmentActivity) {
        viewModel.setProjectImprovement()
        adapter = ProjectImprovementAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvSs.setHasFixedSize(true)
            rvSs.layoutManager = LinearLayoutManager(activity)
            rvSs.adapter = adapter

            createPi.setOnClickListener {
                /*Intent(activity,ProjectImprovementCreateWizard::class.java).also {
                    startActivity(it)
                }*/
                val direction = ProjectImprovementFragmentDirections.actionProjectImprovementFragmentToProjectImprovementCreateWizard("Create PI")
                it.findNavController().navigate(direction)
            }
        }

        adapter.setProjectImprovementSystemCallback(object : ProjectSystemCallback {
            override fun onItemClicked(data: ProjectImprovementItem) {
                Toast.makeText(activity, data.piNo, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.getProjectImprovement().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
            }
        })
    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_black)

        setToolbar(toolbar, "Project Improvement (PI)")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.filter_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                if (!findNavController().popBackStack()) activity?.finish()
            }
            R.id.filter_menu -> {
                initNavigationMenu()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initNavigationMenu() {

        binding.apply {

            val drawer = drawerFilter

            // open drawer at start
            drawer.openDrawer(GravityCompat.END)

            filterStartDate.setOnClickListener {
                /*DatePickerCustom.dialogDatePicker(
                    context = this@ProjectImprovementActivity, fragmentManager = supportFragmentManager,
                    themeDark = false, minDateIsCurrentDate = true
                )*/
            }

            filterEndDate.setOnClickListener {
                /*DatePickerCustom.dialogDatePicker(
                    context = this@ProjectImprovementActivity, fragmentManager = supportFragmentManager,
                    themeDark = false, minDateIsCurrentDate = true
                )*/
            }

            btnCloseFilter.setOnClickListener {
                drawer.closeDrawer(GravityCompat.END)
            }

            btnApply.setOnClickListener {
                Toast.makeText(activity,  "Apply filter", Toast.LENGTH_LONG).show()
                drawer.closeDrawer(GravityCompat.END)
            }
        }
    }

}