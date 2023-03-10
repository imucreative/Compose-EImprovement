package com.fastrata.eimprovement.features.approval.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import android.R.layout.simple_list_item_1
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import com.fastrata.eimprovement.databinding.FragmentListApprovalBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.approval.data.model.ApprovalModel
import com.fastrata.eimprovement.features.approval.data.model.ApprovalRemoteRequest
import com.fastrata.eimprovement.features.changespoint.ui.ChangesPointCreateViewModel
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementViewModel
import com.fastrata.eimprovement.features.suggestionsystem.ui.SuggestionSystemViewModel
import com.fastrata.eimprovement.featuresglobal.data.model.BranchItem
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposalItem
import com.fastrata.eimprovement.featuresglobal.data.model.SubBranchItem
import com.fastrata.eimprovement.featuresglobal.transaction.UpdateStatusProposalCp
import com.fastrata.eimprovement.featuresglobal.transaction.UpdateStatusProposalPi
import com.fastrata.eimprovement.featuresglobal.transaction.UpdateStatusProposalSs
import com.fastrata.eimprovement.featuresglobal.viewmodel.BranchViewModel
import com.fastrata.eimprovement.featuresglobal.viewmodel.StatusProposalViewModel
import com.fastrata.eimprovement.ui.setToolbar
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.Tools.hideKeyboard
import kotlinx.coroutines.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.Exception

class ListApprovalFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentListApprovalBinding? = null
    private val binding get() = _binding!!
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var listApproveViewModel: ListApprovalViewModel
    private lateinit var listSsViewModel: SuggestionSystemViewModel
    private lateinit var listPiViewModel: ProjectImprovementViewModel
    private lateinit var listCpViewModel: ChangesPointCreateViewModel
    private lateinit var masterDataStatusProposalViewModel: StatusProposalViewModel
    private lateinit var masterBranchViewModel: BranchViewModel

    private lateinit var adapter: ListApprovalAdapter
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

    private val args : ListApprovalFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListApprovalBinding.inflate(inflater, container, false)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        context ?: return binding.root

        docId = args.docId.toString()

        listApproveViewModel = injectViewModel(viewModelFactory)
        masterDataStatusProposalViewModel = injectViewModel(viewModelFactory)
        masterBranchViewModel = injectViewModel(viewModelFactory)
        listSsViewModel = injectViewModel(viewModelFactory)
        listPiViewModel = injectViewModel(viewModelFactory)
        listCpViewModel = injectViewModel(viewModelFactory)

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

        adapter = ListApprovalAdapter()
        adapter.notifyDataSetChanged()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getDataListApproval()
    }

    private fun getDataListApproval(){
        try {
            adapter.clear()
            page = 1

            val listApprovalRemoteRequest = ApprovalRemoteRequest(
                userId, limit, page, roleName, APPR,
                userName = userName, docNo = docId, statusId = 0, title = "", orgId = 0,
                warehouseId = 0, startDate = "", endDate = ""
            )

            listApproveViewModel.setListApproval(listApprovalRemoteRequest)
        } catch (e: Exception){
            Timber.e("Error setListPi : $e")
            HelperLoading.hideLoading()
            Toast.makeText(requireContext(), "Error : $e", Toast.LENGTH_LONG).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true);

        _binding = FragmentListApprovalBinding.bind(view)

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

                            val listApprovalRemoteRequest = ApprovalRemoteRequest(
                                userId, limit, page, roleName, APPR,
                                userName = userName, docNo = edtNoDoc.text.toString(), statusId = statusProposalId,
                                title = edtTitle.text.toString(), orgId = branchId, warehouseId = subBranchId,
                                startDate = startDate, endDate = endDate
                            )

                            listApproveViewModel.setListApproval(listApprovalRemoteRequest)
                            getListApproval()
                        }
                    }

                }
            })

            swipe.setOnRefreshListener {
                swipe.isRefreshing = true
                page = 1

                clearFormFilter()

                try {
                    adapter.clear()

                    val listApprovalRemoteRequest = ApprovalRemoteRequest(
                        userId, limit, page, roleName, APPR,
                        userName = userName, docNo = "", statusId = 0, title = "", orgId = 0,
                        warehouseId = 0, startDate = "", endDate = ""
                    )

                    listApproveViewModel.setListApproval(listApprovalRemoteRequest)
                    swipe.isRefreshing = false
                } catch (e: Exception){
                    Timber.e("Error setListApproval : $e")
                    Toast.makeText(requireContext(), "Error : $e", Toast.LENGTH_LONG).show()
                }
            }
        }

        retrieveDataStatusProposal()
        retrieveDataBranch()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clearFormFilter() {
        binding.apply {
            edtNoDoc.setText("")
            edtStatusProposal.setText("")
            edtTitle.setText("")
            edtBranch.setText("")
            edtSubBranch.setText("")
            edtFromDate.setText("")
            edtToDate.setText("")
            statusProposalId = 0
            branchId = 0
            subBranchId = 0
        }
    }

    override fun onResume() {
        super.onResume()
        getListApproval()
    }

    private fun getListApproval() {
        try {
            isLoading = true
            listApproveViewModel.getListApprovalItem.observeEvent(this) { resultObserve ->
                resultObserve.observe(viewLifecycleOwner) { result ->
                    if (result != null) {
                        when (result.status) {
                            Result.Status.LOADING -> {
                                HelperLoading.displayLoadingWithText(requireContext(), "", false)
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
                                isLoading = false
                                Toast.makeText(
                                    requireContext(),
                                    "Error : ${result.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                                Timber.d("###-- Error get List CP")
                            }
                        }
                    }
                }
            }
        }catch (err: Exception){
            HelperLoading.hideLoading()
            isLoading = false
            Toast.makeText(
                requireContext(),
                "Error : ${err.message}",
                Toast.LENGTH_LONG
            ).show()
            Timber.d("###-- Error get List CP")
        }
    }

    private fun retrieveDataStatusProposal(){
        try {
            masterDataStatusProposalViewModel.getStatusProposalItem.observeEvent(this) { resultObserve ->
                resultObserve.observe(viewLifecycleOwner) { result ->
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
                                Toast.makeText(
                                    requireContext(),
                                    "Error : ${result.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                                Timber.d("###-- Error get status proposal")
                            }

                        }

                    }
                }
            }
        }catch (err : Exception){
            binding.edtStatusProposal.isEnabled = false
            Toast.makeText(
                requireContext(),
                "Error : ${err.message}",
                Toast.LENGTH_LONG
            ).show()
            Timber.d("###-- Error get status proposal")
        }
    }

    private fun retrieveDataBranch(){
        try {
            masterBranchViewModel.getBranchItem.observeEvent(this) { resultObserve ->
                resultObserve.observe(viewLifecycleOwner) { result ->
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
                                Toast.makeText(
                                    requireContext(),
                                    "Error : ${result.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                                Timber.d("###-- Error get Branch")
                            }

                        }

                    }
                }
            }
        }catch (err : Exception){
            binding.edtBranch.isEnabled = false
            Toast.makeText(
                requireContext(),
                "Error : ${err.message}",
                Toast.LENGTH_LONG
            ).show()
            Timber.d("###-- Error get Branch")
        }
    }

    private fun retrieveDataSubBranch(){
        try {
            masterBranchViewModel.getSubBranchItem.observeEvent(this) { resultObserve ->
                resultObserve.observe(viewLifecycleOwner) { result ->
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
                                Toast.makeText(
                                    requireContext(),
                                    "Error : ${result.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                                Timber.d("###-- Error get sub Branch")
                            }

                        }

                    }
                }
            }
        }catch (err : Exception){
            binding.edtSubBranch.isEnabled = false
            Toast.makeText(
                requireContext(),
                "Error : ${err.message}",
                Toast.LENGTH_LONG
            ).show()
            Timber.d("###-- Error get sub Branch")
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
        adapter.setApprovalCallback(object : ListApprovalCallback {
            override fun onItemClicked(data: ApprovalModel) {
                when (data.type) {
                    SS -> {
                        notification.showListEdit(requireActivity(),
                            data.typeNo, SS,
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
                            listener = object : HelperNotification.CallbackList {
                                override fun onView() {
                                    val direction =
                                        ListApprovalFragmentDirections.actionListApprovalFragmentToSuggestionSystemCreateWizard(
                                            toolbarTitle = "Detail Sistem Saran",
                                            action = DETAIL,
                                            idSs = data.id,
                                            ssNo = data.typeNo,
                                            type = APPR,
                                            statusProposal = data.status
                                        )
                                    requireView().findNavController().navigate(direction)
                                }

                                override fun onEdit() {

                                }

                                override fun onSubmit() {

                                }

                                override fun onCheck() {
                                    notification.showNotificationYesNo(
                                        requireActivity(), requireContext(), R.color.yellow_800,
                                        "Pengecekan Proposal", resources.getString(R.string.check_desc),
                                        "Pengecekan", resources.getString(R.string.cancel),
                                        object : HelperNotification.CallBackNotificationYesNo {
                                            override fun onNotificationNo() {

                                            }
                                            override fun onNotificationYes() {
                                                lifecycleScope.launch {
                                                    UpdateStatusProposalSs(
                                                        listSsViewModel,
                                                        context = requireContext(),
                                                    ).getDetailDataSs(
                                                        id = data.id,
                                                        ssNo = data.typeNo,
                                                        statusProposal = data.status,
                                                        userNameSubmit = userId,
                                                    ) {
                                                        if (it) {
                                                            Timber.e("### $it")

                                                            val direction =
                                                                ListApprovalFragmentDirections.actionListApprovalFragmentToSuggestionSystemCreateWizard(
                                                                    toolbarTitle = "Pengecekan Sistem Saran",
                                                                    action = APPROVE,
                                                                    idSs = data.id,
                                                                    ssNo = data.typeNo,
                                                                    type = APPR,
                                                                    statusProposal = data.status
                                                                )
                                                            requireView().findNavController().navigate(direction)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    )
                                }

                                override fun onCheckFinal() {
                                    val direction =
                                        ListApprovalFragmentDirections.actionListApprovalFragmentToSuggestionSystemCreateWizard(
                                            toolbarTitle = "Pengecekan Sistem Saran",
                                            action = APPROVE,
                                            idSs = data.id,
                                            ssNo = data.typeNo,
                                            type = APPR,
                                            statusProposal = data.status
                                        )
                                    requireView().findNavController().navigate(direction)
                                }

                                override fun onImplementation() {

                                }

                                override fun onSubmitLaporan() {

                                }

                                override fun onReview() {
                                    notification.showNotificationYesNo(
                                        requireActivity(), requireContext(), R.color.yellow_800,
                                        "Review Proposal", resources.getString(R.string.review_desc),
                                        "Review", resources.getString(R.string.cancel),
                                        object : HelperNotification.CallBackNotificationYesNo {
                                            override fun onNotificationNo() {

                                            }
                                            override fun onNotificationYes() {
                                                lifecycleScope.launch {
                                                    UpdateStatusProposalSs(
                                                        listSsViewModel,
                                                        context = requireContext(),
                                                    ).getDetailDataSs(
                                                        id = data.id,
                                                        ssNo = data.typeNo,
                                                        statusProposal = data.status,
                                                        userNameSubmit = userId,
                                                    ) {
                                                        if(it){
                                                            Timber.e("### $it")

                                                            val direction =
                                                                ListApprovalFragmentDirections.actionListApprovalFragmentToSuggestionSystemCreateWizard(
                                                                    toolbarTitle = "Review Sistem Saran",
                                                                    action = APPROVE,
                                                                    idSs = data.id,
                                                                    ssNo = data.typeNo,
                                                                    type = APPR,
                                                                    statusProposal = data.status
                                                                )
                                                            requireView().findNavController().navigate(direction)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    )
                                }

                                override fun onReviewFinal() {
                                    val direction =
                                        ListApprovalFragmentDirections.actionListApprovalFragmentToSuggestionSystemCreateWizard(
                                            toolbarTitle = "Review Sistem Saran",
                                            action = APPROVE,
                                            idSs = data.id,
                                            ssNo = data.typeNo,
                                            type = APPR,
                                            statusProposal = data.status
                                        )
                                    requireView().findNavController().navigate(direction)
                                }

                                override fun onDelete() {

                                }
                            }
                        )
                    }
                    PI -> {
                        notification.showListEdit(requireActivity(),
                            data.typeNo, PI,
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
                            listener = object : HelperNotification.CallbackList {
                                override fun onView() {
                                    val direction =
                                        ListApprovalFragmentDirections.actionListApprovalFragmentToProjectImprovementCreateWizard(
                                            toolbarTitle = "Detail Project Improvement",
                                            action = DETAIL,
                                            idPi = data.id,
                                            piNo = data.typeNo,
                                            type = APPR,
                                            statusProposal = data.status
                                        )
                                    requireView().findNavController().navigate(direction)
                                }

                                override fun onEdit() {

                                }

                                override fun onSubmit() {

                                }

                                override fun onCheck() {
                                    notification.showNotificationYesNo(
                                        requireActivity(), requireContext(), R.color.blue_800,
                                        "Pengecekan Proposal", resources.getString(R.string.check_desc),
                                        "Pengecekan", resources.getString(R.string.cancel),
                                        object : HelperNotification.CallBackNotificationYesNo {
                                            override fun onNotificationNo() {

                                            }
                                            override fun onNotificationYes() {
                                                lifecycleScope.launch {
                                                    UpdateStatusProposalPi(
                                                        listPiViewModel,
                                                        context = requireContext(),
                                                    ).getDetailDataPi(
                                                        id = data.id,
                                                        piNo = data.typeNo,
                                                        statusProposal = data.status,
                                                        userNameSubmit = userId,
                                                    ) {
                                                        if (it) {
                                                            Timber.e("### $it")

                                                            val direction =
                                                                ListApprovalFragmentDirections.actionListApprovalFragmentToProjectImprovementCreateWizard(
                                                                    toolbarTitle = "Pengecekan Project Improvement",
                                                                    action = APPROVE,
                                                                    idPi = data.id,
                                                                    piNo = data.typeNo,
                                                                    type = APPR,
                                                                    statusProposal = data.status
                                                                )
                                                            requireView().findNavController().navigate(direction)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    )
                                }

                                override fun onCheckFinal() {
                                    val direction =
                                        ListApprovalFragmentDirections.actionListApprovalFragmentToProjectImprovementCreateWizard(
                                            toolbarTitle = "Pengecekan Project Improvement",
                                            action = APPROVE,
                                            idPi = data.id,
                                            piNo = data.typeNo,
                                            type = APPR,
                                            statusProposal = data.status
                                        )
                                    requireView().findNavController().navigate(direction)
                                }

                                override fun onImplementation() {

                                }

                                override fun onSubmitLaporan() {

                                }

                                override fun onReview() {
                                    notification.showNotificationYesNo(
                                        requireActivity(), requireContext(), R.color.blue_800,
                                        "Review Proposal", resources.getString(R.string.review_desc),
                                        "Review", resources.getString(R.string.cancel),
                                        object : HelperNotification.CallBackNotificationYesNo {
                                            override fun onNotificationNo() {

                                            }
                                            override fun onNotificationYes() {
                                                lifecycleScope.launch {
                                                    UpdateStatusProposalPi(
                                                        listPiViewModel,
                                                        context = requireContext(),
                                                    ).getDetailDataPi(
                                                        id = data.id,
                                                        piNo = data.typeNo,
                                                        statusProposal = data.status,
                                                        userNameSubmit = userId,
                                                    ) {
                                                        if (it) {
                                                            Timber.e("### $it")

                                                            val direction =
                                                                ListApprovalFragmentDirections.actionListApprovalFragmentToProjectImprovementCreateWizard(
                                                                    toolbarTitle = "Review Project Improvement",
                                                                    action = APPROVE,
                                                                    idPi = data.id,
                                                                    piNo = data.typeNo,
                                                                    type = APPR,
                                                                    statusProposal = data.status
                                                                )
                                                            requireView().findNavController().navigate(direction)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    )
                                }

                                override fun onReviewFinal() {
                                    val direction =
                                        ListApprovalFragmentDirections.actionListApprovalFragmentToProjectImprovementCreateWizard(
                                            toolbarTitle = "Review Project Improvement",
                                            action = APPROVE,
                                            idPi = data.id,
                                            piNo = data.typeNo,
                                            type = APPR,
                                            statusProposal = data.status
                                        )
                                    requireView().findNavController().navigate(direction)
                                }

                                override fun onDelete() {

                                }
                            }
                        )
                    }
                    CP -> {
                        notification.showListEdit(requireActivity(),
                            data.typeNo, CP,
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
                            listener = object : HelperNotification.CallbackList {
                                override fun onView() {
                                    val direction =
                                        ListApprovalFragmentDirections.actionListApprovalFragmentToChangePointCreateWizard(
                                            toolbarTitle = "Detail Penukaran Poin",
                                            action = DETAIL,
                                            idCp = data.id,
                                            cpNo = data.typeNo,
                                            type = APPR,
                                            statusProposal = data.status
                                        )
                                    requireView().findNavController().navigate(direction)
                                }

                                override fun onEdit() {

                                }

                                override fun onSubmit() {

                                }

                                override fun onCheck() {
                                    notification.showNotificationYesNo(
                                        requireActivity(), requireContext(), R.color.green_800,
                                        "Pengecekan Penukaran Poin", resources.getString(R.string.check_desc),
                                        "Pengecekan", resources.getString(R.string.cancel),
                                        object : HelperNotification.CallBackNotificationYesNo {
                                            override fun onNotificationNo() {

                                            }
                                            override fun onNotificationYes() {
                                                lifecycleScope.launch {
                                                    UpdateStatusProposalCp(
                                                        listCpViewModel,
                                                        context = requireContext(),
                                                    ).getDetailDataCp(
                                                        id = data.id,
                                                        cpNo = data.typeNo,
                                                        statusProposal = data.status,
                                                        userNameSubmit = userId,
                                                    ) {
                                                        if (it) {
                                                            Timber.e("### $it")

                                                            val direction =
                                                                ListApprovalFragmentDirections.actionListApprovalFragmentToChangePointCreateWizard(
                                                                    toolbarTitle = "Review Penukaran Poin",
                                                                    action = APPROVE,
                                                                    idCp = data.id,
                                                                    cpNo = data.typeNo,
                                                                    type = APPR,
                                                                    statusProposal = data.status
                                                                )
                                                            requireView().findNavController()
                                                                .navigate(direction)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    )
                                }

                                override fun onCheckFinal() {

                                }

                                override fun onImplementation() {

                                }

                                override fun onSubmitLaporan() {

                                }

                                override fun onReview() {
                                    val direction =
                                        ListApprovalFragmentDirections.actionListApprovalFragmentToChangePointCreateWizard(
                                            toolbarTitle = "Review Penukaran Poin",
                                            action = APPROVE,
                                            idCp = data.id,
                                            cpNo = data.typeNo,
                                            type = APPR,
                                            statusProposal = data.status
                                        )
                                    requireView().findNavController().navigate(direction)
                                }

                                override fun onReviewFinal() {

                                }

                                override fun onDelete() {

                                }
                            }
                        )
                    }
                }
            }
        })

        getListApproval()
    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_black)

        setToolbar(toolbar, "Daftar Persetujuan")
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

                        val listApprovalRemoteRequest = ApprovalRemoteRequest(
                            userId, limit, page, roleName, APPR,
                            userName = userName, docNo = edtNoDoc.text.toString(), statusId = statusProposalId,
                            title = edtTitle.text.toString(), orgId = branchId, warehouseId = subBranchId,
                            startDate = startDate, endDate = endDate
                        )

                        listApproveViewModel.setListApproval(listApprovalRemoteRequest)
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