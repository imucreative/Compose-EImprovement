package com.fastrata.eimprovement.features.dashboard.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.ui.setToolbar
import android.view.MenuInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.databinding.FragmentDashboardBinding
import com.fastrata.eimprovement.databinding.ToolbarDashboardBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.dashboard.ui.data.BalanceCreateViewModel
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import timber.log.Timber
import javax.inject.Inject

class DashboardFragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var toolbarBinding: ToolbarDashboardBinding
    private lateinit var notification: HelperNotification
    private lateinit var datePicker: DatePickerCustom
    private var greetings: String = ""
    private var userId : Int = 0
    private lateinit var balanceViewModel : BalanceCreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        toolbarBinding = ToolbarDashboardBinding.bind(binding.root)
        context ?: return binding.root

        balanceViewModel = injectViewModel(viewModelFactory)

        notification = HelperNotification()
        userId = HawkUtils().getDataLogin().USER_ID

        datePicker = DatePickerCustom(
            context = binding.root.context, themeDark = true,
            minDateIsCurrentDate = true, parentFragmentManager
        )

        setHasOptionsMenu(true);
        greetings = "${resources.getString(R.string.welcome_user)} ${HawkUtils().getDataLogin().FULL_NAME}"

        initToolbar()
        initData()
        initComponent(requireActivity())
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        binding.apply {
            saldoTxt.text = "Rp. 0"
            countApproval.text = "0 Available"
            countApprovalMenu.text = "0 Available"
        }
        getDataBalance()
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        HelperLoading.hideLoading()
    }

    private fun getDataBalance() {
        try {
            balanceViewModel.userId = userId
            balanceViewModel.observeGetBalance.observe(viewLifecycleOwner, Observer { result ->
                //resultObserve.observe(viewLifecycleOwner,{ result->
                    if (result != null){
                        when(result.status){
                            Result.Status.LOADING  -> {
                                //HelperLoading.displayLoadingWithText(requireContext(),"",false)
                                Timber.d("###-- Loading get balance")
                            }
                            Result.Status.SUCCESS -> {
                                //HelperLoading.hideLoading()
                                binding.apply {
                                    val valCountApproval = result.data!!.data[0].countApproval
                                    val valTotal = result.data.data[0].total

                                    HawkUtils().setDataBalance(valTotal)
                                    saldoTxt.text = Tools.doubleToRupiah(valTotal.toDouble(), 2)
                                    countApproval.text = "$valCountApproval Available"
                                    countApprovalMenu.text = "$valCountApproval Available"
                                }
                            }
                            Result.Status.ERROR -> {
                                //HelperLoading.hideLoading()
                                Toast.makeText(requireContext(),"Error : ${result.message}",Toast.LENGTH_LONG).show()
                                Timber.d("###-- Loading error balance $result")
                            }
                        }
                    }
                //})
            })
        }catch (e : Exception){
            //HelperLoading.hideLoading()
            Timber.e("Error balance : $e")
            Toast.makeText(requireContext(),"Error : $e",Toast.LENGTH_LONG).show()
        }
    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbarDash
        toolbar.setNavigationIcon(R.drawable.ic_menu)

        setToolbar(toolbar, greetings)
    }

    private fun initData(){
        Timber.e("data Login :${HawkUtils().getDataLogin().ROLES}")

        val checkMenuApproval = HawkUtils().getDataLogin().ROLES?.filter { code -> code.MENU_CODE == "6" }
        val checkMenuPi = HawkUtils().getDataLogin().ROLES?.filter { code -> code.MENU_CODE == "8" }
        val checkMenuSs = HawkUtils().getDataLogin().ROLES?.filter { code -> code.MENU_CODE == "7" }
        val checkMenuCp = HawkUtils().getDataLogin().ROLES?.filter { code -> code.MENU_CODE == "9" }

        if (checkMenuApproval?.size == 0) {
            binding.apply {
                btnListApproval.isEnabled = false
                btnListApproval.isClickable = false
                btnListApproval.isFocusable = false
                btnListApproval.setBackgroundColor(resources.getColor(R.color.blue_grey_200))
                menuApproval.isEnabled = false
                menuApproval.isClickable = false
                menuApproval.isFocusable = false
            }
        }

        if (checkMenuPi?.size == 0) {
            binding.apply {
                btnProjectImprovement.isEnabled = false
                btnProjectImprovement.isClickable = false
                btnProjectImprovement.isFocusable = false
                btnProjectImprovement.setBackgroundColor(resources.getColor(R.color.blue_grey_200))
                menuProjectImprovement.isEnabled = false
                menuProjectImprovement.isClickable = false
                menuProjectImprovement.isFocusable = false
            }
        }

        if (checkMenuSs?.size == 0) {
            binding.apply {
                btnSuggestionSystem.isEnabled = false
                btnSuggestionSystem.isClickable = false
                btnSuggestionSystem.isFocusable = false
                btnSuggestionSystem.setBackgroundColor(resources.getColor(R.color.blue_grey_200))
                menuSuggestionSystem.isEnabled = false
                menuSuggestionSystem.isClickable = false
                menuSuggestionSystem.isFocusable = false
            }
        }

        if (checkMenuCp?.size == 0) {
            binding.apply {
                btnChangePoint.isEnabled = false
                btnChangePoint.isClickable = false
                btnChangePoint.isFocusable = false
                btnChangePoint.setBackgroundColor(resources.getColor(R.color.blue_grey_200))
                menuPointExchange.isEnabled = false
                menuPointExchange.isClickable = false
                menuPointExchange.isFocusable = false
            }
        }
    }

    private fun initComponent(activity: FragmentActivity) {
        val docId = ""
        binding.apply {
            welcome.text = greetings

            linearSaldo.setOnClickListener {
                val directions = DashboardFragmentDirections.actionDashboardFragmentToMutasiFragment(resources.getString(R.string.detail_balance))
                it.findNavController().navigate(directions)
            }

            btnListApproval.setOnClickListener {
                val direction = DashboardFragmentDirections.actionDashboardFragmentToListApprovalFragment(resources.getString(R.string.list_approval))
                it.findNavController().navigate(direction)
            }

            btnSuggestionSystem.setOnClickListener {
                val direction = DashboardFragmentDirections.actionDashboardFragmentToSuggestionSystemFragment(resources.getString(R.string.suggestion_system), docId = docId)
                it.findNavController().navigate(direction)
            }

            btnProjectImprovement.setOnClickListener {
                val direction = DashboardFragmentDirections.actionDashboardFragmentToProjectImprovementFragment(resources.getString(R.string.project_improvement), docId = docId)
                it.findNavController().navigate(direction)
            }

            btnChangePoint.setOnClickListener {
                val direction = DashboardFragmentDirections.actionDashboardFragmentToChangesPointFragment(resources.getString(R.string.change_point), docId = docId)
                it.findNavController().navigate(direction)
            }

            // action menu
            menuApproval.setOnClickListener {
                val direction = DashboardFragmentDirections.actionDashboardFragmentToListApprovalFragment(resources.getString(R.string.list_approval), docId = docId)
                it.findNavController().navigate(direction)
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            menuSuggestionSystem.setOnClickListener {
                val direction = DashboardFragmentDirections.actionDashboardFragmentToSuggestionSystemFragment(resources.getString(R.string.suggestion_system), docId = docId)
                it.findNavController().navigate(direction)
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            menuProjectImprovement.setOnClickListener {
                val direction = DashboardFragmentDirections.actionDashboardFragmentToProjectImprovementFragment(resources.getString(R.string.project_improvement), docId = docId)
                it.findNavController().navigate(direction)
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            menuPointExchange.setOnClickListener {
                val direction = DashboardFragmentDirections.actionDashboardFragmentToChangesPointFragment(resources.getString(R.string.change_point), docId = docId)
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
            toolbarBinding.toolbarDash,
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
