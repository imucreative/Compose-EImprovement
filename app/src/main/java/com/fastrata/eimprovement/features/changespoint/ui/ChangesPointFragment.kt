package com.fastrata.eimprovement.features.changespoint.ui

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
import com.fastrata.eimprovement.databinding.FragmentChangesPointSystemBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointModel
import com.fastrata.eimprovement.ui.setToolbar
import javax.inject.Inject

class ChangesPointFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentChangesPointSystemBinding
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var viewModel: ChangesPointViewModel
    private lateinit var adapter: ChangesPointAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangesPointSystemBinding.inflate(inflater, container, false)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        context ?: return binding.root

        viewModel = injectViewModel(viewModelFactory)
        //viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ChangesPointViewModel::class.java)

        setHasOptionsMenu(true);

        initToolbar()
        initComponent(requireActivity())

        return binding.root
    }

    private fun initComponent(activity: FragmentActivity) {
        viewModel.setSuggestionSystem()
        adapter = ChangesPointAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvSs.setHasFixedSize(true)
            rvSs.layoutManager = LinearLayoutManager(activity)
            rvSs.adapter = adapter

            createSs.setOnClickListener {
                /*Intent(activity, ChangesPointCreateWizard::class.java).also {
                    startActivity(it)
                }*/
                val direction = ChangesPointFragmentDirections.actionChangesPointFragmentToChangesPointCreateWizard("Create Changes Point")
                it.findNavController().navigate(direction)
            }
        }

        adapter.setChangeRewardCallback(object : ChangesPointCallback {
            override fun onItemClicked(data: ChangePointModel) {
                Toast.makeText(activity, data.no_penukaran, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.getSuggestionSystem().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
            }
        })
    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_black)

        setToolbar(toolbar, "Change Point")
    }

    private fun initNavigationMenu() {

        binding.apply {

            val drawer = drawerFilter

            // open drawer at start
            drawer.openDrawer(GravityCompat.END)

            filterStartDate.setOnClickListener {
                /*DatePickerCustom.dialogDatePicker(
                    context = this@ChangesPointActivity, fragmentManager = supportFragmentManager,
                    themeDark = false, minDateIsCurrentDate = true
                )*/
            }

            filterEndDate.setOnClickListener {
                /*DatePickerCustom.dialogDatePicker(
                    context = this@ChangesPointActivity, fragmentManager = supportFragmentManager,
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
}