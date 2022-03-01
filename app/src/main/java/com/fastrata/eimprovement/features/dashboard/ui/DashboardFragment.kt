package com.fastrata.eimprovement.features.dashboard.ui

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.ui.setToolbar
import android.view.MenuInflater
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fastrata.eimprovement.HomeActivity
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.databinding.DialogFormCreateSebabMasalahBinding
import com.fastrata.eimprovement.databinding.DialogListCalendarDashboardBinding
import com.fastrata.eimprovement.databinding.FragmentDashboardBinding
import com.fastrata.eimprovement.databinding.ToolbarDashboardBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.dashboard.ui.data.BalanceCreateViewModel
import com.fastrata.eimprovement.features.dashboard.ui.data.CalendarDashboardModel
import com.fastrata.eimprovement.features.splashscreen.SplashScreenActivity
import com.fastrata.eimprovement.featuresglobal.transaction.CheckUserActive
import com.fastrata.eimprovement.featuresglobal.viewmodel.CheckUserViewModel
import com.fastrata.eimprovement.utils.*
import com.marcohc.robotocalendar.RobotoCalendarView.RobotoCalendarListener
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class DashboardFragment: Fragment(), Injectable, RobotoCalendarListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var bindingDialogListCalendar: DialogListCalendarDashboardBinding
    private lateinit var toolbarBinding: ToolbarDashboardBinding
    private lateinit var notification: HelperNotification
    private lateinit var datePicker: DatePickerCustom
    private var greetings: String = ""
    private var userId : Int = 0
    private lateinit var dialog: Dialog
    private lateinit var balanceViewModel : BalanceCreateViewModel
    private lateinit var checkUserViewModel : CheckUserViewModel
    private lateinit var calendar: Calendar
    private lateinit var calendarThisMonth: List<CalendarDashboardModel>
    private var thisMonth: Int = 0
    private var thisYear: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        toolbarBinding = ToolbarDashboardBinding.bind(binding.root)
        context ?: return binding.root

        balanceViewModel = injectViewModel(viewModelFactory)
        checkUserViewModel = injectViewModel(viewModelFactory)

        notification = HelperNotification()
        userId = HawkUtils().getDataLogin().USER_ID

        calendar = Calendar.getInstance()
        thisMonth = calendar[Calendar.MONTH] + 1
        thisYear = calendar[Calendar.YEAR]

        datePicker = DatePickerCustom(
            context = binding.root.context, themeDark = true,
            minDateIsCurrentDate = true, parentFragmentManager
        )

        setHasOptionsMenu(true)
        greetings = "${resources.getString(R.string.welcome_user)} ${HawkUtils().getDataLogin().FULL_NAME}"

        initToolbar()
        initData()
        initComponent()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.apply {
            saldoTxt.text = "Rp. 0"
            countApproval.text = "0 Available"
            countApprovalMenu.text = "0 Available"
        }
        getDataBalance()
        checkUser()
    }

    override fun onResume() {
        super.onResume()
        HelperLoading.hideLoading()
    }

    private fun getDataBalance() {
        try {
            balanceViewModel.setBalance(userId)
            balanceViewModel.getBalance.observeEvent(this) { resultObserve ->
                resultObserve.observe(viewLifecycleOwner) { result ->
                    if (result != null) {
                        when (result.status) {
                            Result.Status.LOADING -> {
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
                                Toast.makeText(
                                    requireContext(),
                                    "Error : ${result.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                                Timber.d("###-- Loading error balance $result")
                            }
                        }
                    }
                }
            }
        }catch (e : Exception){
            //HelperLoading.hideLoading()
            Timber.e("Error balance : $e")
            Toast.makeText(requireContext(),"Error : $e",Toast.LENGTH_LONG).show()
        }
    }

    private fun checkUser() {
        CheckUserActive(
            checkUserViewModel,
            requireContext(),
            viewLifecycleOwner
        ).getUserActive(userId){
            HawkUtils().setUserActive(it)
            if (!HawkUtils().getUserActive()){
                dialogLogOut()
            }
        }
    }

    private fun dialogLogOut() {
        if (!HawkUtils().getUserActive()) {
            notification.showNotificationYesNo(
                requireActivity(),
                requireContext(),
                R.color.blue_500,
                resources.getString(R.string.log_out),
                resources.getString(R.string.account_inactive),
                resources.getString(R.string.ok),
                resources.getString(R.string.cancel),
                object : HelperNotification.CallBackNotificationYesNo {
                    override fun onNotificationNo() {

                    }

                    override fun onNotificationYes() {
                        HawkUtils().setStatusLogin(false)
                        HawkUtils().setDataLogin(null)
                        HawkUtils().removeDataCreateProposal(SS)
                        HawkUtils().removeDataCreateProposal(CP)
                        HawkUtils().removeDataCreateProposal(PI)
                        activity?.finish()
                        HomeActivity.stopAMQPConsumer()
                        startActivity(Intent(activity, SplashScreenActivity::class.java))
                    }
                }
            )
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
                menuApproval.setBackgroundColor(resources.getColor(R.color.blue_grey_200))
                imageView.visibility = VISIBLE
            }
        } else {
            binding.apply {
                initCalendar()
                robotoCalendarPicker.visibility = VISIBLE
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
                menuProjectImprovement.setBackgroundColor(resources.getColor(R.color.blue_grey_200))
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
                menuSuggestionSystem.setBackgroundColor(resources.getColor(R.color.blue_grey_200))
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
                menuPointExchange.setBackgroundColor(resources.getColor(R.color.blue_grey_200))
            }
        }
    }

    private fun initCalendar(){
        binding.apply {
            // https://github.com/marcohc/roboto-calendar-view
            robotoCalendarPicker.setRobotoCalendarListener(this@DashboardFragment)
            robotoCalendarPicker.setShortWeekDays(false)
            robotoCalendarPicker.showDateTitle(true)
            robotoCalendarPicker.date = Date()
        }

        addEventOnCalendar(thisYear, thisMonth)
    }

    private fun addEventOnCalendar(varThisYear: Int, varThisMonth: Int) {
        try {
            balanceViewModel.setCalendarDashboard(varThisYear, varThisMonth)
            balanceViewModel.getCalendarDashboard.observeEvent(this@DashboardFragment) { resultObserve ->
                resultObserve.observe(viewLifecycleOwner) { result ->
                    if (result != null) {
                        when (result.status) {
                            Result.Status.LOADING -> {
                                Timber.d("###-- Loading get balance")
                            }
                            Result.Status.SUCCESS -> {
                                binding.apply {
                                    calendarThisMonth = result.data!!.data

                                    calendarThisMonth.forEach { data ->
                                        val docType = data.DOC_TYPE
                                        val getDate = data.GET_DATE

                                        calendar[Calendar.DAY_OF_MONTH] = getDate
                                        calendar[Calendar.MONTH] = thisMonth - 1 // (-1 to index)
                                        calendar[Calendar.YEAR] = thisYear

                                        when (docType) {
                                            SS -> robotoCalendarPicker.markCircleImage1(calendar.time)
                                            PI -> robotoCalendarPicker.markCircleImage2(calendar.time)
                                            else -> {}
                                        }
                                    }
                                }
                            }
                            Result.Status.ERROR -> {
                                Toast.makeText(requireContext(), "Error : ${result.message}", Toast.LENGTH_LONG).show()
                                Timber.d("###-- Loading error balance $result")
                            }
                        }
                    }
                }
            }
        } catch (e : Exception) {
            Timber.e("Error balance : $e")
            Toast.makeText(requireContext(),"Error : $e",Toast.LENGTH_LONG).show()
        }
    }

    private fun initComponent() {
        val docId = ""
        binding.apply {
            welcome.text = greetings

            linearSaldo.setOnClickListener {
                val directions = DashboardFragmentDirections.actionDashboardFragmentToMutasiFragment(resources.getString(R.string.detail_balance))
                it.findNavController().navigate(directions)
            }

            btnListApproval.setOnClickListener {
                if (HawkUtils().getUserActive()) {
                    val direction = DashboardFragmentDirections.actionDashboardFragmentToListApprovalFragment(resources.getString(R.string.list_approval))
                    it.findNavController().navigate(direction)
                } else {
                    dialogLogOut()
                }
            }

            btnSuggestionSystem.setOnClickListener {
                if (HawkUtils().getUserActive()) {
                    val direction = DashboardFragmentDirections.actionDashboardFragmentToSuggestionSystemFragment(resources.getString(R.string.suggestion_system), docId = docId)
                    it.findNavController().navigate(direction)
                } else {
                    dialogLogOut()
                }
            }

            btnProjectImprovement.setOnClickListener {
                if (HawkUtils().getUserActive()) {
                    val direction = DashboardFragmentDirections.actionDashboardFragmentToProjectImprovementFragment(resources.getString(R.string.project_improvement), docId = docId)
                    it.findNavController().navigate(direction)
                } else {
                    dialogLogOut()
                }
            }

            btnChangePoint.setOnClickListener {
                if (HawkUtils().getUserActive()) {
                    val direction = DashboardFragmentDirections.actionDashboardFragmentToChangesPointFragment(resources.getString(R.string.change_point), docId = docId)
                    it.findNavController().navigate(direction)
                } else {
                    dialogLogOut()
                }
            }

            // action menu
            menuApproval.setOnClickListener {
                if (HawkUtils().getUserActive()) {
                    val direction = DashboardFragmentDirections.actionDashboardFragmentToListApprovalFragment(resources.getString(R.string.list_approval))
                    it.findNavController().navigate(direction)
                } else {
                    dialogLogOut()
                }
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            menuSuggestionSystem.setOnClickListener {
                if (HawkUtils().getUserActive()) {
                    val direction = DashboardFragmentDirections.actionDashboardFragmentToSuggestionSystemFragment(resources.getString(R.string.suggestion_system), docId = docId)
                    it.findNavController().navigate(direction)
                } else {
                    dialogLogOut()
                }
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            menuProjectImprovement.setOnClickListener {
                if (HawkUtils().getUserActive()) {
                    val direction = DashboardFragmentDirections.actionDashboardFragmentToProjectImprovementFragment(resources.getString(R.string.project_improvement), docId = docId)
                    it.findNavController().navigate(direction)
                } else {
                    dialogLogOut()
                }
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            menuPointExchange.setOnClickListener {
                if (HawkUtils().getUserActive()) {
                    val direction = DashboardFragmentDirections.actionDashboardFragmentToChangesPointFragment(resources.getString(R.string.change_point), docId = docId)
                    it.findNavController().navigate(direction)
                } else {
                    dialogLogOut()
                }
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

    // https://github.com/marcohc/roboto-calendar-view
    override fun onDayClick(date: Date) {
        try {
            val today = date.formatToServerDateDefaults()
            Toast.makeText(requireContext(), "onDayClick: $today", Toast.LENGTH_SHORT).show()

            if (!calendarThisMonth.isNullOrEmpty()) {
                /*calendarThisMonth.forEach {
                    if (it.CREATED_DATE.substring(0, 10) == date.formatToServerDateDefaults()){
                        Timber.e(it.toString())
                        dialogListProposalCalendar(requireActivity())
                    }
                }*/
                val checkListCalendar = calendarThisMonth.filter {
                    it.CREATED_DATE.substring(0, 10) == today
                }
                if (checkListCalendar.isNotEmpty()){
                    Timber.e(checkListCalendar.size.toString())
                    dialogListProposalCalendar(requireActivity(), today)
                }
            }
        } catch (e: Exception){
            Toast.makeText(requireContext(), "Error. $e", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDayLongClick(date: Date?) {

    }

    override fun onRightButtonClick() {
        if (thisMonth == 12){
            thisMonth = 1
            thisYear += 1
        } else {
            thisMonth += 1
        }

        /*Timber.e(thisMonth.toString())
        Timber.e(thisYear.toString())
        Timber.e(calendar.time.toString())*/

        addEventOnCalendar(thisYear, thisMonth)
    }

    override fun onLeftButtonClick() {
        if (thisMonth == 1){
            thisMonth = 12
            thisYear -= 1
        } else {
            thisMonth -= 1
        }

        addEventOnCalendar(thisYear, thisMonth)
    }

    private fun dialogListProposalCalendar(activity: Activity, calendarToday: String) {
        bindingDialogListCalendar = DialogListCalendarDashboardBinding.inflate(layoutInflater)

        dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.setContentView(bindingDialogListCalendar.root)
        dialog.setCancelable(true)

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT

        bindingDialogListCalendar.apply {
            today.text = calendarToday
            btnClose.setOnClickListener { dialog.dismiss() }
        }

        dialog.show()
        dialog.window!!.attributes = lp
    }

}
