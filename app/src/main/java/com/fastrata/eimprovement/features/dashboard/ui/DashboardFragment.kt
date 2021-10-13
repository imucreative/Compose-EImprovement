package com.fastrata.eimprovement.features.dashboard.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentDashboardBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.utils.DatePickerCustom
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.HelperNotification
import com.fastrata.eimprovement.ui.setToolbar
import android.view.MenuInflater
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fastrata.eimprovement.di.Injectable
import javax.inject.Inject

class DashboardFragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var notification: HelperNotification
    private lateinit var datePicker: DatePickerCustom

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        context ?: return binding.root

        notification = HelperNotification()

        datePicker = DatePickerCustom(
            context = binding.root.context, themeDark = true,
            minDateIsCurrentDate = true, parentFragmentManager
        )

        setHasOptionsMenu(true);

        initToolbar()
        initComponent(requireActivity())

        return binding.root
    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_menu)

        setToolbar(toolbar, "Dashboard")
    }

    private fun initComponent(activity: FragmentActivity) {
        binding.apply {
            Toast.makeText(activity,resources.getString(R.string.welcome_user)+" "+HawkUtils().getDataLogin().FULL_NAME,Toast.LENGTH_LONG).show()
            welcome.text = "${resources.getString(R.string.welcome_user)} ${HawkUtils().getDataLogin().FULL_NAME}"

            linearSaldo.setOnClickListener {
                notification.showNotification(activity,resources.getString(R.string.point_balanca), "50000")
            }
            saldoTxt.text = "50000"
            filterActivityDate.setOnClickListener {
                datePicker.showDialog(object : DatePickerCustom.Callback {
                    override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                        val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                        val mon = month + 1
                        val monthStr = if (mon < 10) "0$mon" else "$mon"

                        Toast.makeText(activity, "$dayStr-$monthStr-$year", Toast.LENGTH_LONG).show()
                    }
                })
            }

            // action menu
            menuApproval.setOnClickListener {
                val direction = DashboardFragmentDirections.actionDashboardFragmentToListApprovalFragment(resources.getString(R.string.list_approval))
                it.findNavController().navigate(direction)

                drawerLayout.closeDrawer(GravityCompat.START)
            }
            menuSuggestionSystem.setOnClickListener {
                val direction = DashboardFragmentDirections.actionDashboardFragmentToSuggestionSystemFragment(resources.getString(R.string.suggestion_system))
                it.findNavController().navigate(direction)

                drawerLayout.closeDrawer(GravityCompat.START)
            }
            menuProjectImprovement.setOnClickListener {
                val direction = DashboardFragmentDirections.actionDashboardFragmentToProjectImprovementFragment(resources.getString(R.string.project_improvement))
                it.findNavController().navigate(direction)

                drawerLayout.closeDrawer(GravityCompat.START)
            }
            menuPointExchange.setOnClickListener {
                val direction = DashboardFragmentDirections.actionDashboardFragmentToChangesPointFragment(resources.getString(R.string.change_point))
                it.findNavController().navigate(direction)

                drawerLayout.closeDrawer(GravityCompat.START)
            }

        }
    }

    private fun initNavigationMenu() {
        val drawer = binding.drawerLayout

        val toggle = object : ActionBarDrawerToggle(
            activity,
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.settings_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                initNavigationMenu()
            }
            R.id.setting_menu -> {
                val direction = DashboardFragmentDirections.actionDashboardFragmentToSettingsFragment(resources.getString(R.string.setting))
                findNavController().navigate(direction)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}