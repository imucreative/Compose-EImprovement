package com.fastrata.eimprovement.features.suggestionsystem.ui

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import android.R.layout.simple_list_item_1
import android.widget.AdapterView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.databinding.FragmentSuggestionSystemBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemRemoteRequest
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemModel
import com.fastrata.eimprovement.featuresglobal.data.model.BranchItem
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposalItem
import com.fastrata.eimprovement.featuresglobal.data.model.SubBranchItem
import com.fastrata.eimprovement.featuresglobal.viewmodel.BranchViewModel
import com.fastrata.eimprovement.featuresglobal.viewmodel.CheckPeriodViewModel
import com.fastrata.eimprovement.featuresglobal.viewmodel.StatusProposalViewModel
import com.fastrata.eimprovement.ui.setToolbar
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.Tools.hideKeyboard
import timber.log.Timber
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SuggestionSystemFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentSuggestionSystemBinding? = null
    private val binding get() = _binding!!
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var listSsViewModel: SuggestionSystemViewModel
    private lateinit var masterDataStatusProposalViewModel: StatusProposalViewModel
    private lateinit var checkPeriodViewModel: CheckPeriodViewModel
    private lateinit var masterBranchViewModel: BranchViewModel

    private lateinit var adapter: SuggestionSystemAdapter
    private lateinit var datePicker: DatePickerCustom

    private var listStatusProposalItem: List<StatusProposalItem>? = null
    private var listBranchItem: List<BranchItem>? = null
    private var listSubBranchItem: List<SubBranchItem>? = null
    private lateinit var selectedStatusProposal: StatusProposalItem
    private lateinit var selectedBranch: BranchItem
    private lateinit var selectedSubBranch: SubBranchItem
    private var statusProposalId = 0
    private var branchId = 0
    private var subBranchId = 0
    lateinit var fromDate: Date
    lateinit var toDate: Date
    private var userId: Int = 0
    private var userName: String = ""
    private var limit: Int = 10
    private var page: Int = 1
    private var totalPage: Int = 1
    private var isLoading = false
    private var roleName: String = ""
    private val sdf = SimpleDateFormat("dd-MM-yyyy")
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var notification: HelperNotification

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuggestionSystemBinding.inflate(inflater, container, false)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        context ?: return binding.root

        listSsViewModel = injectViewModel(viewModelFactory)
        masterDataStatusProposalViewModel = injectViewModel(viewModelFactory)
        masterBranchViewModel = injectViewModel(viewModelFactory)
        checkPeriodViewModel = injectViewModel(viewModelFactory)

        datePicker = DatePickerCustom(
            context = binding.root.context, themeDark = true,
            minDateIsCurrentDate = false, fragmentManager = parentFragmentManager
        )

        try {
            userId = HawkUtils().getDataLogin().USER_ID
            userName = HawkUtils().getDataLogin().USER_NAME
            roleName = HawkUtils().getDataLogin().ROLE_NAME

            val listSsRemoteRequest = SuggestionSystemRemoteRequest(
                userId, limit, page, roleName,
                userName = userName, ssNo = "", statusId = 0, title = "", category = "",orgId = 0,
                warehouseId = 0, startDate = "", endDate = ""
            )

            listSsViewModel.setListSs(listSsRemoteRequest)
        } catch (e: Exception){
            Timber.e("Error setListSs : $e")
            Toast.makeText(requireContext(), "Error : $e", Toast.LENGTH_LONG).show()
        }

        try {
            masterDataStatusProposalViewModel.setStatusProposal()
        } catch (e: Exception){
            Timber.e("Error setStatusProposal : $e")
            Toast.makeText(requireContext(), "Error : $e", Toast.LENGTH_LONG).show()
        }

        try {
            masterBranchViewModel.setBranch()
        } catch (e: Exception){
            Timber.e("Error setBranch : $e")
            Toast.makeText(requireContext(), "Error : $e", Toast.LENGTH_LONG).show()
        }

        adapter = SuggestionSystemAdapter()
        adapter.notifyDataSetChanged()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true);

        _binding = FragmentSuggestionSystemBinding.bind(view)

        initToolbar()
        initComponent()
        initNavigationMenu()

        binding.apply {
            layoutManager = LinearLayoutManager(activity)
            rv.setHasFixedSize(true)
            rv.layoutManager = layoutManager
            rv.adapter = adapter

            rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                    val total  = adapter.itemCount

                    if (!isLoading && page < totalPage){
                        if (visibleItemCount + pastVisibleItem >= total){
                            page++

                            if (!edtStatusProposal.text.isNullOrEmpty()) {
                                statusProposalId = selectedStatusProposal.id
                            }

                            if (!edtBranch.text.isNullOrEmpty()) {
                                branchId = selectedBranch.orgId
                            }

                            if (!edtSubBranch.text.isNullOrEmpty()) {
                                subBranchId = selectedSubBranch.warehouseId
                            }

                            val listSsRemoteRequest = SuggestionSystemRemoteRequest(
                                userId, limit, page, roleName,
                                userName = userName, ssNo = edtNoSs.text.toString(), statusId = statusProposalId,
                                title = edtTitle.text.toString(), category = edtCategory.text.toString(),
                                orgId = branchId, warehouseId = subBranchId, startDate = edtFromDate.text.toString(),
                                endDate = edtToDate.text.toString()
                            )

                            listSsViewModel.setListSs(listSsRemoteRequest)
                            getListSs()
                        }
                    }

                }
            })

            create.setOnClickListener {
                try {
                    checkPeriodViewModel.setCheckPeriod(SS)

                    getStatusCheckPeriod()
                } catch (e: Exception){
                    Timber.e("Error setCheckPeriod : $e")
                    Toast.makeText(requireContext(), "Error : $e", Toast.LENGTH_LONG).show()
                }
            }

            swipe.setOnRefreshListener {
                swipe.isRefreshing= true
                page = 1

                clearFormFilter()

                try {
                    adapter.clear()

                    val listSsRemoteRequest = SuggestionSystemRemoteRequest(
                        userId, limit, page, roleName,
                        userName = userName, ssNo = "", statusId = 0, title = "", category = "", orgId = 0,
                        warehouseId = 0, startDate = "", endDate = ""
                    )

                    listSsViewModel.setListSs(listSsRemoteRequest)
                    swipe.isRefreshing= false
                } catch (e: Exception){
                    Timber.e("Error setListSs : $e")
                    Toast.makeText(requireContext(), "Error : $e", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clearFormFilter() {
        binding.apply {
            edtNoSs.setText("")
            edtStatusProposal.setText("")
            edtTitle.setText("")
            edtCategory.setText("")
            edtBranch.setText("")
            edtSubBranch.setText("")
            edtFromDate.setText("")
            edtToDate.setText("")
            statusProposalId = 0
            branchId = 0
            subBranchId = 0
        }
    }

    private fun getListSs() {
        isLoading = true

        listSsViewModel.getListSsItem.observeEvent(this) { resultObserve ->
            resultObserve.observe(viewLifecycleOwner, { result ->
                if (result != null) {
                    when (result.status) {
                        Result.Status.LOADING -> {
                            HelperLoading.displayLoadingWithText(requireContext(),"",false)
                            Timber.d("###-- Loading get List SS")
                        }
                        Result.Status.SUCCESS -> {
                            HelperLoading.hideLoading()

                            val listResponse = result.data?.data
                            if (listResponse != null) {
                                if (page == 0 && listResponse.isEmpty()) {
                                    binding.rv.visibility = View.GONE
                                    binding.noDataScreen.root.visibility = View.VISIBLE
                                } else {
                                    totalPage = result.data.totalPage

                                    binding.rv.visibility = View.VISIBLE
                                    binding.noDataScreen.root.visibility = View.GONE

                                    adapter.setList(listResponse)
                                }
                            }

                            retrieveDataStatusProposal()
                            retrieveDataBranch()
                            isLoading = false
                            Timber.d("###-- Success get List SS")
                        }
                        Result.Status.ERROR -> {
                            HelperLoading.hideLoading()
                            isLoading = false
                            Timber.d("###-- Error get List SS")
                        }

                    }

                }
            })
        }
    }

    private fun retrieveDataStatusProposal(){
        masterDataStatusProposalViewModel.getStatusProposalItem.observeEvent(this) { resultObserve ->
            resultObserve.observe(viewLifecycleOwner, { result ->
                if (result != null) {
                    when (result.status) {
                        Result.Status.LOADING -> {
                            binding.edtStatusProposal.isEnabled = false
                            Timber.d("###-- Loading get status proposal")
                        }
                        Result.Status.SUCCESS -> {
                            binding.edtStatusProposal.isEnabled = true
                            listStatusProposalItem = result.data?.data
                            initComponentStatusProposal()
                            Timber.d("###-- Success get status proposal")
                        }
                        Result.Status.ERROR -> {
                            binding.edtStatusProposal.isEnabled = false
                            Timber.d("###-- Error get status proposal")
                        }

                    }

                }
            })
        }
    }

    private fun getStatusCheckPeriod(){
        notification = HelperNotification()
        checkPeriodViewModel.getCheckPeriodItem.observeEvent(this) { resultObserve ->
            resultObserve.observe(viewLifecycleOwner, { result ->
                if (result != null) {
                    when (result.status) {
                        Result.Status.LOADING -> {
                            HelperLoading.displayLoadingWithText(requireContext(),"",false)
                            Timber.d("###-- Loading get CheckPeriod")
                        }
                        Result.Status.SUCCESS -> {
                            HelperLoading.hideLoading()
                            val statusProposal = result.data?.data?.get(0)
                            if (statusProposal?.id == 11) {
                                notification.shownotificationyesno(
                                    requireActivity(),
                                    "",
                                    resources.getString(R.string.title_past_period),
                                    object : HelperNotification.CallBackNotificationYesNo {
                                        override fun onNotificationNo() {

                                        }

                                        override fun onNotificationYes() {
                                            val direction = SuggestionSystemFragmentDirections.actionSuggestionSystemFragmentToSuggestionSystemCreateWizard(
                                                toolbarTitle = "Create Suggestion System", action = ADD, idSs = 0, ssNo = "", type = "", statusProposal = statusProposal
                                            )
                                            requireView().findNavController().navigate(direction)
                                        }
                                    }
                                )
                            } else {
                                val direction = SuggestionSystemFragmentDirections.actionSuggestionSystemFragmentToSuggestionSystemCreateWizard(
                                    toolbarTitle = "Create Suggestion System", action = ADD, idSs = 0, ssNo = "", type = "", statusProposal = statusProposal
                                )
                                requireView().findNavController().navigate(direction)
                            }

                            Timber.d("###-- Success get CheckPeriod")
                        }
                        Result.Status.ERROR -> {
                            HelperLoading.hideLoading()
                            Timber.d("###-- Error get CheckPeriod")
                        }

                    }

                }
            })
        }
    }

    private fun retrieveDataBranch(){
        masterBranchViewModel.getBranchItem.observeEvent(this) { resultObserve ->
            resultObserve.observe(viewLifecycleOwner, { result ->
                if (result != null) {
                    when (result.status) {
                        Result.Status.LOADING -> {
                            binding.edtBranch.isEnabled = false
                            Timber.d("###-- Loading get Branch")
                        }
                        Result.Status.SUCCESS -> {
                            listBranchItem = result.data?.data
                            initComponentBranch()
                            binding.edtBranch.isEnabled = true
                            Timber.d("###-- Success get Branch")
                        }
                        Result.Status.ERROR -> {
                            binding.edtBranch.isEnabled = false
                            Timber.d("###-- Error get Branch")
                        }

                    }

                }
            })
        }
    }

    private fun retrieveDataSubBranch(){
        masterBranchViewModel.getSubBranchItem.observeEvent(this) { resultObserve ->
            resultObserve.observe(viewLifecycleOwner, { result ->
                if (result != null) {
                    when (result.status) {
                        Result.Status.LOADING -> {
                            binding.edtSubBranch.isEnabled = false
                            Timber.d("###-- Loading get sub Branch")
                        }
                        Result.Status.SUCCESS -> {
                            binding.edtSubBranch.isEnabled = true
                            listSubBranchItem = result.data?.data
                            initComponentSubBranch()
                            Timber.d("###-- Success get sub Branch")
                        }
                        Result.Status.ERROR -> {
                            binding.edtSubBranch.isEnabled = false
                            Timber.d("###-- Error get sub Branch")
                        }

                    }

                }
            })
        }
    }

    private fun initComponentStatusProposal() {
        binding.apply {
            val adapter = ArrayAdapter(
                requireContext(), simple_list_item_1,
                listStatusProposalItem!!.map { value ->
                    value.status
                }
            )
            edtStatusProposal.setAdapter(adapter)
            edtStatusProposal.onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, view, i, l ->
                    selectedStatusProposal = listStatusProposalItem!![i]
                    hideKeyboard()
                }
        }
    }

    private fun initComponentBranch() {
        binding.apply {
            val adapter = ArrayAdapter(
                requireContext(), simple_list_item_1,
                listBranchItem!!.map { value ->
                    value.branch
                }
            )
            edtBranch.setAdapter(adapter)
            edtBranch.onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, view, i, l ->
                    selectedBranch = listBranchItem!![i]
                    edtSubBranch.setText("")
                    masterBranchViewModel.setSubBranch(listBranchItem!![i].orgId)
                    retrieveDataSubBranch()
                    hideKeyboard()

                }
        }
    }

    private fun initComponentSubBranch() {
        binding.apply {
            val adapter = ArrayAdapter(
                requireContext(), simple_list_item_1,
                listSubBranchItem!!.map { value ->
                    value.subBranchName
                }
            )
            edtSubBranch.setAdapter(adapter)
            edtSubBranch.onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, view, i, l ->
                    selectedSubBranch = listSubBranchItem!![i]
                    hideKeyboard()

                }
        }
    }

    private fun initComponent() {
        adapter.setSuggestionSystemCallback(object : SuggestionSystemCallback{
            override fun onItemClicked(data: SuggestionSystemModel) {

                HelperNotification().showListEdit(requireActivity(),resources.getString(R.string.select),
                    view = true,
                    viewEdit = true,
                    viewImplementation = true,
                    viewDelete = true,object : HelperNotification.CallbackList{
                        override fun onView() {
                            val direction = SuggestionSystemFragmentDirections.actionSuggestionSystemFragmentToSuggestionSystemCreateWizard(
                                toolbarTitle = "View Suggestion System", action = DETAIL, idSs = data.idSs, ssNo = data.ssNo, type = "", statusProposal = data.status
                            )
                            requireView().findNavController().navigate(direction)
                        }

                        override fun onEdit() {
                            val direction = SuggestionSystemFragmentDirections.actionSuggestionSystemFragmentToSuggestionSystemCreateWizard(
                                toolbarTitle = "Edit Suggestion System", action = EDIT, idSs = data.idSs, ssNo = data.ssNo, type = "", statusProposal = data.status
                            )
                            requireView().findNavController().navigate(direction)
                        }

                        override fun onImplementation() {
                            val direction = SuggestionSystemFragmentDirections.actionSuggestionSystemFragmentToSuggestionSystemCreateWizard(
                                toolbarTitle = "Implement Suggestion System", action = EDIT, idSs = data.idSs, ssNo = data.ssNo, type = "", statusProposal = data.status
                            )
                            requireView().findNavController().navigate(direction)
                        }

                        override fun onDelete() {
                            Toast.makeText(requireContext(),"Data Belum Terhapus",Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        })

        getListSs()
    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_black)

        setToolbar(toolbar, "Suggestion System (SS)")
    }

    private fun initNavigationMenu() {
        binding.apply {
            // open drawer at start
            edtFromDate.setOnClickListener {
                datePicker.showDialog(object : DatePickerCustom.Callback {
                    override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                        val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                        val mon = month + 1
                        val monthStr = if (mon < 10) "0$mon" else "$mon"
                        edtFromDate.setText("$year-$monthStr-$dayStr")
                        fromDate = sdf.parse(edtFromDate.text.toString())
                        edtToDate.text!!.clear()
                    }
                })
            }

            edtToDate.setOnClickListener {
                datePicker.showDialog(object : DatePickerCustom.Callback {
                    override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                        val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                        val mon = month + 1
                        val monthStr = if (mon < 10) "0$mon" else "$mon"
                        edtToDate.setText("$year-$monthStr-$dayStr")
                        toDate = sdf.parse(edtToDate.text.toString())
                        if (edtFromDate.text.isNullOrEmpty()){
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.wrong_field),
                                R.drawable.ic_close, R.color.red_500)
                            edtFromDate.requestFocus()
                        }else{
                            if (!toDate.after(fromDate)){
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, resources, root.context,
                                    resources.getString(R.string.wrong_field),
                                    R.drawable.ic_close, R.color.red_500)
                                edtToDate.text!!.clear()
                            }
                        }
                    }
                })
            }

            btnCloseFilter.setOnClickListener {
                drawerFilter.closeDrawer(GravityCompat.END)
            }

            btnApply.setOnClickListener {
                if (!edtFromDate.text.isNullOrEmpty() && edtToDate.text.isNullOrEmpty()) {
                    SnackBarCustom.snackBarIconInfo(
                        root, layoutInflater, resources, root.context,
                        resources.getString(R.string.date_empty),
                        R.drawable.ic_close, R.color.red_500)
                } else {
                    if (!edtStatusProposal.text.isNullOrEmpty()) {
                        statusProposalId = selectedStatusProposal.id
                    }

                    if (!edtBranch.text.isNullOrEmpty()) {
                        branchId = selectedBranch.orgId
                    }

                    if (!edtSubBranch.text.isNullOrEmpty()) {
                        subBranchId = selectedSubBranch.warehouseId
                    }

                    page = 1

                    try {
                        adapter.clear()

                        val listSsRemoteRequest = SuggestionSystemRemoteRequest(
                            userId, limit, page, roleName,
                            userName = userName, ssNo = edtNoSs.text.toString(), statusId = statusProposalId,
                            title = edtTitle.text.toString(), category = edtCategory.text.toString(), orgId = branchId,
                            warehouseId = subBranchId, startDate = edtFromDate.text.toString(),
                            endDate = edtToDate.text.toString()
                        )

                        listSsViewModel.setListSs(listSsRemoteRequest)
                    } catch (e: Exception){
                        Timber.e("Error setListSs : $e")
                        Toast.makeText(requireContext(), "Error : $e", Toast.LENGTH_LONG).show()
                    }

                    drawerFilter.closeDrawer(GravityCompat.END)
                }
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
                binding.drawerFilter.openDrawer(GravityCompat.END)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}