package com.fastrata.eimprovement.features.changespoint.ui

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import android.R.layout.simple_list_item_1
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.databinding.FragmentChangesPointSystemBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointModel
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointRemoteRequest
import com.fastrata.eimprovement.featuresglobal.data.model.BranchItem
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposalItem
import com.fastrata.eimprovement.featuresglobal.data.model.SubBranchItem
import com.fastrata.eimprovement.featuresglobal.transaction.UpdateStatusProposalCp
import com.fastrata.eimprovement.featuresglobal.viewmodel.BranchViewModel
import com.fastrata.eimprovement.featuresglobal.viewmodel.CheckPeriodViewModel
import com.fastrata.eimprovement.featuresglobal.viewmodel.StatusProposalViewModel
import com.fastrata.eimprovement.ui.setToolbar
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.Tools.hideKeyboard
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ChangesPointFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentChangesPointSystemBinding? = null
    private val binding get() = _binding!!
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var listCpViewModel: ChangesPointCreateViewModel
    private lateinit var masterDataStatusProposalViewModel: StatusProposalViewModel
    private lateinit var checkPeriodViewModel: CheckPeriodViewModel
    private lateinit var masterBranchViewModel: BranchViewModel

    private lateinit var adapter: ChangesPointAdapter
    private lateinit var datePicker: DatePickerCustom

    private var listStatusProposalItem: List<StatusProposalItem>? = null
    private var listBranchItem: List<BranchItem>? = null
    private var listSubBranchItem: List<SubBranchItem>? = null
    private var statusProposalId = 0
    private var branchId = 0
    private var subBranchId = 0
    private var userId: Int = 0
    private var userName: String = ""
    private var limit: Int = 10
    private var page: Int = 1
    private var totalPage: Int = 1
    private var isLoading = false
    private var roleName: String = ""
    private var docId = ""
    private val formatDateDisplay = SimpleDateFormat("dd/MM/yyyy")
    private val formatDateOriginalValue = SimpleDateFormat("yyyy-MM-dd")
    private lateinit var fromDate: Date
    private lateinit var toDate: Date
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var notification: HelperNotification
    private lateinit var selectedStatusProposal: StatusProposalItem
    private lateinit var selectedBranch: BranchItem
    private lateinit var selectedSubBranch: SubBranchItem

    private val args : ChangesPointFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangesPointSystemBinding.inflate(inflater, container, false)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        context ?: return binding.root

        docId = args.docId.toString()

        listCpViewModel = injectViewModel(viewModelFactory)
        masterDataStatusProposalViewModel = injectViewModel(viewModelFactory)
        masterBranchViewModel = injectViewModel(viewModelFactory)
        checkPeriodViewModel = injectViewModel(viewModelFactory)

        notification = HelperNotification()

        datePicker = DatePickerCustom(
            context = binding.root.context, themeDark = true,
            minDateIsCurrentDate = false, fragmentManager = parentFragmentManager
        )

        userId = HawkUtils().getDataLogin().USER_ID
        userName = HawkUtils().getDataLogin().USER_NAME
        roleName  = HawkUtils().getDataLogin().ROLE_NAME

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

        adapter = ChangesPointAdapter()
        adapter.notifyDataSetChanged()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getDataListCp()
    }

    override fun onResume() {
        super.onResume()
       getListCp()
    }

    private fun getDataListCp(){
        try{
            adapter.clear()
            page = 1
            val listChangePointRemoteRequest = ChangePointRemoteRequest(
                userId, limit, page, roleName, CP,
                userName = userName, cpNo = docId, statusId = 0, description = "", createdBy = "", orgId = 0,
                warehouseId = 0, startDate = "", endDate = ""
            )

            listCpViewModel.setListCp(listChangePointRemoteRequest)
        }catch (e: Exception){
            Timber.e("Error setListCp : $e")
            Toast.makeText(requireContext(),"Error : $e",Toast.LENGTH_LONG).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true);

        _binding = FragmentChangesPointSystemBinding.bind(view)

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

                            val startDate = if(edtFromDate.text.toString() == "") "" else formatDateOriginalValue.format(fromDate)
                            val endDate = if(edtToDate.text.toString() == "") "" else formatDateOriginalValue.format(toDate)

                            val listChangePointRemoteRequest = ChangePointRemoteRequest(
                                userId, limit, page, roleName, CP,
                                userName = userName, cpNo = edtNoCp.text.toString(), statusId = statusProposalId,
                                description = edtKeterangan.text.toString(), createdBy = edtCreatedBy.text.toString(),
                                orgId = branchId, warehouseId = subBranchId, startDate = startDate, endDate = endDate
                            )

                            listCpViewModel.setListCp(listChangePointRemoteRequest)
                            getListCp()
                        }
                    }

                }
            })

            createSs.setOnClickListener {
                try {
                    checkPeriodViewModel.setCheckPeriod(CP)

                    getStatusCheckPeriod()
                }catch (e: Exception){
                    Timber.e("Error setCheckPeriod : $e")
                    Toast.makeText(requireContext(), "Error : $e", Toast.LENGTH_LONG).show()
                }
            }

            swipe.setOnRefreshListener {
                swipe.isRefreshing= true
                page = 1

                clearFormFilter()

                try{
                    adapter.clear()

                    val listChangePointRemoteRequest = ChangePointRemoteRequest(
                        userId, limit, page, roleName, CP,
                        userName = userName, cpNo = "", statusId = 0, description = "", createdBy = "", orgId = 0,
                        warehouseId = 0, startDate = "", endDate = ""
                    )

                    listCpViewModel.setListCp(listChangePointRemoteRequest)
                    swipe.isRefreshing  = false
                }catch (e: Exception){
                    Timber.e("Error setListCp : $e")
                    Toast.makeText(requireContext(),"Error : $e",Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    private fun getStatusCheckPeriod() {
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
                                notification.showNotificationYesNo(
                                    requireActivity(),
                                    requireContext(),
                                    R.color.blue_500,
                                    resources.getString(R.string.title_past_period),
                                    "",
                                    resources.getString(R.string.agree),
                                    resources.getString(R.string.not_agree),
                                    object : HelperNotification.CallBackNotificationYesNo {
                                        override fun onNotificationNo() {

                                        }

                                        override fun onNotificationYes() {
                                            val direction = ChangesPointFragmentDirections.actionChangesPointFragmentToChangesPointCreateWizard(
                                                toolbarTitle = "Create Redeem Point", action = ADD , idCp = 0 , cpNo = "", type = "",statusProposal = statusProposal
                                            )
                                            requireView().findNavController().navigate(direction)
                                        }
                                    }
                                )
                            } else {
                                val direction = ChangesPointFragmentDirections.actionChangesPointFragmentToChangesPointCreateWizard(
                                    toolbarTitle = "Create ChangePoint", action = ADD , idCp = 0 , cpNo = "", type = "",statusProposal = statusProposal
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clearFormFilter() {
        binding.apply {
            edtNoCp.setText("")
            edtStatusProposal.setText("")
            edtKeterangan.setText("")
            edtBranch.setText("")
            edtSubBranch.setText("")
            edtFromDate.setText("")
            edtToDate.setText("")
            statusProposalId = 0
            branchId = 0
            subBranchId = 0
        }
    }

    private fun getListCp() {
        isLoading = true

        listCpViewModel.getListCpItem.observeEvent(this){ resultObserve ->
            resultObserve.observe(viewLifecycleOwner, { result ->
                if (result != null) {
                    when (result.status){
                        Result.Status.LOADING -> {
                            HelperLoading.displayLoadingWithText(requireContext(),"",false)
                            Timber.d("###-- Loading get List CP")
                        }
                        Result.Status.SUCCESS -> {
                            HelperLoading.hideLoading()

                            val listResponse = result.data?.data
                            if (listResponse != null) {
                                if (listResponse.isNullOrEmpty()) {
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
                            Timber.d("###-- Success get List CP")
                        }
                        Result.Status.ERROR -> {
                            HelperLoading.hideLoading()
                            Toast.makeText(requireContext(),"Error : ${result.message}", Toast.LENGTH_LONG).show()
                            isLoading = false
                            Timber.d("###-- Error get List CP")
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
                            HelperLoading.hideLoading()
                            Toast.makeText(requireContext(),"Error : ${result.message}", Toast.LENGTH_LONG).show()
                            Timber.d("###-- Error get status proposal")
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
                            binding.edtBranch.isEnabled = true
                            listBranchItem = result.data?.data
                            initComponentBranch()
                            Timber.d("###-- Success get Branch")
                        }
                        Result.Status.ERROR -> {
                            binding.edtBranch.isEnabled = false
                            HelperLoading.hideLoading()
                            Toast.makeText(requireContext(),"Error : ${result.message}", Toast.LENGTH_LONG).show()
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
                            HelperLoading.hideLoading()
                            Toast.makeText(requireContext(),"Error : ${result.message}", Toast.LENGTH_LONG).show()
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
        adapter.setChangeRewardCallback(object : ChangesPointCallback {
            override fun onItemClicked(data: ChangePointModel) {
                notification.showListEdit(requireActivity(),
                    data.cpNo, CP,
                    view = data.isView,
                    viewEdit = data.isEdit,
                    viewSubmit = data.isSubmit,
                    viewImplementation = data.isImplementation,
                    viewCheck = data.isCheck,
                    viewCheckFinal = data.isCheckFinal,
                    viewSubmitLaporan = data.isSubmitlaporan,
                    viewReview = data.isReview,
                    viewReviewFinal = data.isReviewFinal,
                    viewDelete = data.isDelete,
                    listener = object : HelperNotification.CallbackList{
                        override fun onView() {
                            val direction = ChangesPointFragmentDirections.actionChangesPointFragmentToChangesPointCreateWizard(
                                toolbarTitle = "Detail Redeem Point", action = DETAIL,
                                idCp = data.idCp, cpNo = data.cpNo, type = CP, statusProposal = data.status)
                            requireView().findNavController().navigate(direction)
                        }

                        override fun onEdit() {
                            val direction = ChangesPointFragmentDirections.actionChangesPointFragmentToChangesPointCreateWizard(
                                toolbarTitle = "Edit Redeem Point", action = EDIT,
                                idCp = data.idCp, cpNo = data.cpNo, type = CP, statusProposal = data.status)
                            requireView().findNavController().navigate(direction)
                        }

                        override fun onSubmit() {
                            notification.showNotificationYesNo(
                                requireActivity(), requireContext(), R.color.blue_500,
                                "Submit Redeem Point", resources.getString(R.string.submit_desc),
                                "Submit", resources.getString(R.string.no),
                                object : HelperNotification.CallBackNotificationYesNo {
                                    override fun onNotificationNo() {

                                    }
                                    override fun onNotificationYes() {
                                        lifecycleScope.launch {
                                            UpdateStatusProposalCp(
                                                listCpViewModel,
                                                context = requireContext(),
                                            ).getDetailDataCp(
                                                id = data.idCp,
                                                cpNo = data.cpNo,
                                                statusProposal = data.status,
                                                userNameSubmit = userId,
                                            ) {
                                                if (it) {
                                                    //HawkUtils().removeDataCreateProposal(PI_DETAIL_DATA)
                                                    onStart()
                                                    Timber.e("### $it")
                                                }
                                            }
                                        }
                                    }
                                }
                            )
                        }

                        override fun onCheck() {

                        }

                        override fun onCheckFinal() {

                        }

                        override fun onImplementation() {

                        }

                        override fun onSubmitLaporan() {

                        }

                        override fun onReview() {

                        }

                        override fun onReviewFinal() {

                        }

                        override fun onDelete() {
                            notification.showNotificationYesNo(
                                requireActivity(),
                                requireContext(),
                                R.color.blue_500,
                                resources.getString(R.string.delete),
                                resources.getString(R.string.delete_confirmation),
                                resources.getString(R.string.ok),
                                resources.getString(R.string.no),
                                object : HelperNotification.CallBackNotificationYesNo {
                                    override fun onNotificationNo() {
                                    }
                                    override fun onNotificationYes() {
                                        removeListCp(data)
                                    }
                                }
                            )
                        }
                    }
                )

            }
        })

        getListCp()
    }

    private fun removeListCp(data: ChangePointModel) {
        try {
            listCpViewModel.deleteCpList(data.idCp)
            listCpViewModel.doRemoveCp.observeEvent(this@ChangesPointFragment){ resultObserve ->
                resultObserve.observe(viewLifecycleOwner,{result ->
                    Timber.e("### -- $result")
                    if (result != null){
                        when(result.status) {
                            Result.Status.LOADING -> {
                                HelperLoading.displayLoadingWithText(
                                    requireContext(),
                                    "",
                                    false
                                )
                                Timber.d("###-- Loading get doRemoveCp loading")
                            }
                            Result.Status.SUCCESS -> {
                                HelperLoading.hideLoading()
                                getDataListCp()

                                result.data?.let {
                                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                                    Timber.d("###-- Success get doRemoveCp sukses $it")
                                }
                            }
                            Result.Status.ERROR -> {
                                HelperLoading.hideLoading()

                                Toast.makeText(requireContext(), result.data?.message.toString(), Toast.LENGTH_LONG).show()
                                Timber.d("###-- Error get doRemoveCp Error ${result.data}")
                            }
                        }
                    }
                })
            }
        }catch (err: Exception){
            Toast.makeText(requireContext(), "Error doRemove : ${err.message}", Toast.LENGTH_LONG).show()
            Timber.e("### Error doRemoveSs : ${err.message}")
        }
    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_black)

        setToolbar(toolbar, "Redeem Point (RP)")
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

                        fromDate = formatDateOriginalValue.parse("$year-$monthStr-$dayStr")
                        edtFromDate.setText(formatDateDisplay.format(fromDate))

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

                        toDate = formatDateOriginalValue.parse("$year-$monthStr-$dayStr")

                        if (edtFromDate.text.isNullOrEmpty() || !toDate.after(fromDate)){
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.wrong_field),
                                R.drawable.ic_close, R.color.red_500)
                            edtToDate.text!!.clear()
                        } else {
                            edtToDate.setText(formatDateDisplay.format(toDate))
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

                        val startDate = if(edtFromDate.text.toString() == "") "" else formatDateOriginalValue.format(fromDate)
                        val endDate = if(edtToDate.text.toString() == "") "" else formatDateOriginalValue.format(toDate)

                        val listCpRemoteRequest = ChangePointRemoteRequest(
                            userId, limit, page, roleName, CP,
                            userName = userName, cpNo = edtNoCp.text.toString(), statusId = statusProposalId,
                            description = edtKeterangan.text.toString(), createdBy = edtCreatedBy.text.toString(),
                            orgId = branchId, warehouseId = subBranchId, startDate = startDate,
                            endDate = endDate
                        )

                        listCpViewModel.setListCp(listCpRemoteRequest)
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