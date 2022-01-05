package com.fastrata.eimprovement.features.projectimprovement.ui

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import android.R.layout.simple_list_item_1
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.projectimprovement.adapter.ProjectImprovementAdapter
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectSystemCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementRemoteRequest
import com.fastrata.eimprovement.featuresglobal.data.model.BranchItem
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposalItem
import com.fastrata.eimprovement.featuresglobal.data.model.SubBranchItem
import com.fastrata.eimprovement.featuresglobal.transaction.UpdateStatusProposalPi
import com.fastrata.eimprovement.featuresglobal.viewmodel.BranchViewModel
import com.fastrata.eimprovement.featuresglobal.viewmodel.CheckPeriodViewModel
import com.fastrata.eimprovement.featuresglobal.viewmodel.StatusProposalViewModel
import com.fastrata.eimprovement.ui.setToolbar
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.Tools.hideKeyboard
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.Exception

class ProjectImprovementFragment : Fragment(), Injectable{
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentProjectImprovementBinding? = null
    private val binding get() = _binding!!
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var listPiViewModel : ProjectImprovementViewModel
    private lateinit var masterDataStatusProposalViewModel: StatusProposalViewModel
    private lateinit var checkPeriodViewModel: CheckPeriodViewModel
    private lateinit var masterBranchViewModel: BranchViewModel

    private lateinit var adapter : ProjectImprovementAdapter
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
    private var jobLevelId: Int = 0
    private var docId = ""
    private val sdf = SimpleDateFormat("yyyy-MM-dd")
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var notification: HelperNotification

    private val args : ProjectImprovementFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementBinding.inflate(inflater, container, false)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        context ?: return binding.root

        docId = args.docId.toString()

        listPiViewModel = injectViewModel(viewModelFactory)
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
        roleName = HawkUtils().getDataLogin().ROLE_NAME
        jobLevelId = HawkUtils().getDataLogin().POSITION_ID!!

        try {
            masterDataStatusProposalViewModel.setStatusProposal()
        } catch (e: Exception){
            Timber.e("Error setStatusProposal : $e")
            HelperLoading.hideLoading()
            Toast.makeText(requireContext(), "Error : $e", Toast.LENGTH_LONG).show()
        }

        try {
            masterBranchViewModel.setBranch()
        } catch (e: Exception){
            Timber.e("Error setBranch : $e")
            HelperLoading.hideLoading()
            Toast.makeText(requireContext(), "Error : $e", Toast.LENGTH_LONG).show()
        }

        adapter = ProjectImprovementAdapter()
        adapter.notifyDataSetChanged()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getDataListPi()
    }

    private fun getDataListPi(){
        try {
            adapter.clear()
            page = 1

            val listProjectImprovementRemoteRequest = ProjectImprovementRemoteRequest(
                userId, limit, page, roleName, PI,
                userName = userName, piNo = docId, statusId = 0, title = "", orgId = 0,
                warehouseId = 0, startDate = "", endDate = ""
            )

            listPiViewModel.setListPi(listProjectImprovementRemoteRequest)
        } catch (e: Exception){
            Timber.e("Error setListPi : $e")
            HelperLoading.hideLoading()
            Toast.makeText(requireContext(), "Error : $e", Toast.LENGTH_LONG).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true);

        _binding = FragmentProjectImprovementBinding.bind(view)

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

                            val listProjectImprovementRemoteRequest = ProjectImprovementRemoteRequest(
                                userId, limit, page, roleName, PI,
                                userName = userName, piNo = edtNoPi.text.toString(), statusId = statusProposalId,
                                title = edtTitle.text.toString(), orgId = branchId, warehouseId = subBranchId,
                                startDate = edtFromDate.text.toString(), endDate = edtToDate.text.toString()
                            )

                            listPiViewModel.setListPi(listProjectImprovementRemoteRequest)
                            getListPi()
                        }
                    }

                }
            })

            createPi.setOnClickListener {
                when (jobLevelId) {
                    3, 5 -> {
                        try {
                            checkPeriodViewModel.setCheckPeriod(PI)
                            getStatusCheckPeriod()
                        } catch (err: Exception) {
                            Timber.e("Error setCheckPeriod : $err")
                            HelperLoading.hideLoading()
                            Toast.makeText(requireContext(), "Error : $err", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                    else -> {
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            resources.getString(R.string.level_not_eligible),
                            R.drawable.ic_close, R.color.red_500)
                    }
                }
            }

            swipe.setOnRefreshListener {
                swipe.isRefreshing = true
                page = 1

                clearFormFilter()

                try {
                    adapter.clear()

                    val listProjectImprovementRemoteRequest = ProjectImprovementRemoteRequest(
                        userId, limit, page, roleName, PI,
                        userName = userName, piNo = "", statusId = 0, title = "", orgId = 0,
                        warehouseId = 0, startDate = "", endDate = ""
                    )

                    listPiViewModel.setListPi(listProjectImprovementRemoteRequest)
                    swipe.isRefreshing = false
                } catch (e: Exception){
                    Timber.e("Error setListPi : $e")
                    HelperLoading.hideLoading()
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
            edtNoPi.setText("")
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
        getListPi()
    }

    private fun getListPi() {
        isLoading = true
        try {
            listPiViewModel.getListPiItem.observeEvent(this) { resultObserve ->
                resultObserve.observe(viewLifecycleOwner, { result ->
                    if (result != null) {
                        when (result.status) {
                            Result.Status.LOADING -> {
                                HelperLoading.displayLoadingWithText(requireContext(), "", false)
                                Timber.d("###-- Loading get List PI")
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
                                Timber.d("###-- Success get List PI")
                            }
                            Result.Status.ERROR -> {
                                HelperLoading.hideLoading()
                                Toast.makeText(
                                    requireContext(),
                                    "Error : ${result.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                                isLoading = false
                                Timber.d("###-- Error get List PI")
                            }
                        }
                    }
                })
            }
        }catch (err: Exception){
            HelperLoading.hideLoading()
            Toast.makeText(
                requireContext(),
                "Error : ${err.message}",
                Toast.LENGTH_LONG
            ).show()
            isLoading = false
            Timber.d("###-- Error get List PI")
        }
    }

    private fun retrieveDataStatusProposal(){
        try {
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
                                Toast.makeText(
                                    requireContext(),
                                    "Error : ${result.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                                Timber.d("###-- Error get status proposal")
                            }

                        }

                    }
                })
            }
        }catch (err : Exception){
            binding.edtStatusProposal.isEnabled = false
            HelperLoading.hideLoading()
            Toast.makeText(
                requireContext(),
                "Error : ${err.message}",
                Toast.LENGTH_LONG
            ).show()
            Timber.d("###-- Error get status proposal")
        }
    }

    private fun getStatusCheckPeriod(){
        try {
            checkPeriodViewModel.getCheckPeriodItem.observeEvent(this) { resultObserve ->
                resultObserve.observe(viewLifecycleOwner, { result ->
                    if (result != null) {
                        when (result.status) {
                            Result.Status.LOADING -> {
                                HelperLoading.displayLoadingWithText(requireContext(), "", false)
                                Timber.d("###-- Loading get CheckPeriod")
                            }
                            Result.Status.SUCCESS -> {
                                HelperLoading.hideLoading()
                                val statusProposal = result.data?.data?.get(0)
                                Timber.e("Period : $statusProposal")
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
                                                val direction =
                                                    ProjectImprovementFragmentDirections.actionProjectImprovementFragmentToProjectImprovementCreateWizard(
                                                        toolbarTitle = "Create Project Improvement",
                                                        action = ADD,
                                                        idPi = 0,
                                                        piNo = "",
                                                        type = "",
                                                        statusProposal = statusProposal
                                                    )
                                                requireView().findNavController()
                                                    .navigate(direction)
                                            }
                                        }
                                    )
                                } else {
                                    val direction =
                                        ProjectImprovementFragmentDirections.actionProjectImprovementFragmentToProjectImprovementCreateWizard(
                                            toolbarTitle = "Create Project Improvement",
                                            action = ADD,
                                            idPi = 0,
                                            piNo = "",
                                            type = "",
                                            statusProposal = statusProposal
                                        )
                                    requireView().findNavController().navigate(direction)
                                }
                                Timber.d("###-- Success get CheckPeriod")
                            }
                            Result.Status.ERROR -> {
                                HelperLoading.hideLoading()
                                Toast.makeText(
                                    requireContext(),
                                    "Error : ${result.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                                Timber.d("###-- Error get CheckPeriod")
                            }
                        }
                    }
                })
            }
        }catch (err: Exception){
            HelperLoading.hideLoading()
            Toast.makeText(
                requireContext(),
                "Error : ${err.message}",
                Toast.LENGTH_LONG
            ).show()
            Timber.d("###-- Error get CheckPeriod")
        }
    }

    private fun retrieveDataBranch(){
        try {
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
                                Toast.makeText(
                                    requireContext(),
                                    "Error : ${result.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                                Timber.d("###-- Error get Branch")
                            }

                        }

                    }
                })
            }
        }catch (err : Exception){
            binding.edtBranch.isEnabled = false
            HelperLoading.hideLoading()
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
                                Toast.makeText(
                                    requireContext(),
                                    "Error : ${result.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                                Timber.d("###-- Error get sub Branch")
                            }

                        }

                    }
                })
            }
        }catch (err: Exception){
            binding.edtSubBranch.isEnabled = false
            HelperLoading.hideLoading()
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
        adapter.setProjectImprovementSystemCallback(object : ProjectSystemCallback {
            override fun onItemClicked(data: ProjectImprovementModel) {
                notification.showListEdit(requireActivity(),resources.getString(R.string.select),
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
                            val direction = ProjectImprovementFragmentDirections.actionProjectImprovementFragmentToProjectImprovementCreateWizard(
                                toolbarTitle = "Detail Project Improvement", action = DETAIL, idPi = data.idPi, piNo = data.piNo, type = "", statusProposal = data.status
                            )
                            requireView().findNavController().navigate(direction)
                        }

                        override fun onEdit() {
                            val direction = ProjectImprovementFragmentDirections.actionProjectImprovementFragmentToProjectImprovementCreateWizard(
                                toolbarTitle = "Edit Project Improvement", action = EDIT, idPi = data.idPi, piNo = data.piNo, type = "", statusProposal = data.status
                            )
                            requireView().findNavController().navigate(direction)
                        }

                        override fun onSubmit() {
                            notification.showNotificationYesNo(
                                requireActivity(), requireContext(), R.color.blue_500,
                                "Submit Proposal", resources.getString(R.string.submit_desc),
                                "Submit", resources.getString(R.string.no),
                                object : HelperNotification.CallBackNotificationYesNo {
                                    override fun onNotificationNo() {

                                    }
                                    override fun onNotificationYes() {
                                        UpdateStatusProposalPi(
                                            listPiViewModel,
                                            context = requireContext(),
                                            owner = this@ProjectImprovementFragment
                                        ).getDetailDataPi(
                                            id = data.idPi,
                                            piNo = data.piNo,
                                            statusProposal = data.status,
                                            userNameSubmit = userId,
                                        ) {
                                            if(it){
                                                //HawkUtils().removeDataCreateProposal(PI_DETAIL_DATA)
                                                onStart()
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
                            notification.showNotificationYesNo(
                                requireActivity(), requireContext(), R.color.blue_500,
                                "Implementation Proposal", resources.getString(R.string.submit_desc),
                                "Implement", resources.getString(R.string.no),
                                object : HelperNotification.CallBackNotificationYesNo {
                                    override fun onNotificationNo() {

                                    }
                                    override fun onNotificationYes() {
                                        UpdateStatusProposalPi(
                                            listPiViewModel,
                                            context = requireContext(),
                                            owner = this@ProjectImprovementFragment
                                        ).getDetailDataPi(
                                            id = data.idPi,
                                            piNo = data.piNo,
                                            statusProposal = data.status,
                                            userNameSubmit = userId,
                                        ) {
                                            if(it){
                                                onStart()
                                            }
                                        }
                                    }
                                }
                            )
                        }

                        override fun onSubmitLaporan() {
                            val direction = ProjectImprovementFragmentDirections.actionProjectImprovementFragmentToProjectImprovementCreateWizard(
                                toolbarTitle = "Submit Project Improvement", action = SUBMIT_PROPOSAL, idPi = data.idPi, piNo = data.piNo, type = "", statusProposal = data.status
                            )
                            requireView().findNavController().navigate(direction)
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
                                        removeListPi(data)
                                    }
                                }
                            )
                        }
                    }
                )
            }
        })

        getListPi()
    }

    private fun removeListPi(data: ProjectImprovementModel){
        try{
            listPiViewModel.deletePiList(data.idPi)
            listPiViewModel.doRemovePi.observeEvent(this@ProjectImprovementFragment){ resultObserve ->
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
                                Timber.d("###-- Loading get doRemoveSs loading")
                            }
                            Result.Status.SUCCESS  -> {
                                HelperLoading.hideLoading()
                                clearFormFilter()
                                getDataListPi()

                                result.data?.let {
                                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                                    Timber.d("###-- Success get doRemovePi sukses $it")
                                }
                            }
                            Result.Status.ERROR -> {
                                HelperLoading.hideLoading()

                                Toast.makeText(requireContext(), result.data?.message.toString(), Toast.LENGTH_LONG).show()
                                Timber.d("###-- Error get doRemovePi Error ${result.data}")
                            }
                        }
                    }
                })
            }
        }catch (err : Exception){
            Toast.makeText(requireContext(), "Error doRemove : ${err.message}", Toast.LENGTH_LONG).show()
            Timber.e("### Error doRemovePi : ${err.message}")
        }
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
                binding.drawerFilter.openDrawer(GravityCompat.END)
            }
        }
        return super.onOptionsItemSelected(item)
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

                        val listPiRemoteRequest = ProjectImprovementRemoteRequest(
                            userId, limit, page, roleName, PI,
                            userName = userName, piNo = edtNoPi.text.toString(), statusId = statusProposalId,
                            title = edtTitle.text.toString(), orgId = branchId, warehouseId = subBranchId,
                            startDate = edtFromDate.text.toString(), endDate = edtToDate.text.toString()
                        )

                        listPiViewModel.setListPi(listPiRemoteRequest)
                    } catch (e: Exception){
                        Timber.e("Error setListSs : $e")
                        Toast.makeText(requireContext(), "Error : $e", Toast.LENGTH_LONG).show()
                    }

                    drawerFilter.closeDrawer(GravityCompat.END)
                }
            }
        }
    }

}