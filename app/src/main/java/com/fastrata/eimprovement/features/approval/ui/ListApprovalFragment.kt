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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.databinding.FragmentListApprovalBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.approval.data.model.ApprovalModel
import com.fastrata.eimprovement.featuresglobal.data.model.BranchItem
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposalItem
import com.fastrata.eimprovement.featuresglobal.data.model.SubBranchItem
import com.fastrata.eimprovement.featuresglobal.viewmodel.BranchViewModel
import com.fastrata.eimprovement.featuresglobal.viewmodel.StatusProposalViewModel
import com.fastrata.eimprovement.ui.setToolbar
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.Tools.hideKeyboard
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ListApprovalFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentListApprovalBinding? = null
    private val binding get() = _binding!!
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var listApproveViewModel: ListApprovalViewModel
    private lateinit var masterDataStatusProposalViewModel: StatusProposalViewModel
    private lateinit var masterBranchViewModel: BranchViewModel

    private lateinit var adapterList: ListApprovalAdapter
    private lateinit var datePicker: DatePickerCustom

    private var listStatusProposalItem: List<StatusProposalItem>? = null
    private var listBranchItem: List<BranchItem>? = null
    private var listSubBranchItem: List<SubBranchItem>? = null
    private lateinit var selectedStatusProposal: StatusProposalItem
    private lateinit var selectedBranch: BranchItem
    private lateinit var selectedSubBranch: SubBranchItem
    lateinit var fromDate: Date
    lateinit var toDate: Date
    val sdf = SimpleDateFormat("dd-MM-yyyy")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListApprovalBinding.inflate(inflater, container, false)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        context ?: return binding.root

        listApproveViewModel = injectViewModel(viewModelFactory)
        masterDataStatusProposalViewModel = injectViewModel(viewModelFactory)
        masterBranchViewModel = injectViewModel(viewModelFactory)

        datePicker = DatePickerCustom(
            context = binding.root.context, themeDark = true,
            minDateIsCurrentDate = true, parentFragmentManager
        )

        listApproveViewModel.setApproval()

        masterDataStatusProposalViewModel.setStatusProposal()
        masterBranchViewModel.setBranch()

        adapterList = ListApprovalAdapter()
        adapterList.notifyDataSetChanged()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true);

        _binding = FragmentListApprovalBinding.bind(view)

        initToolbar()
        initComponent()

        binding.apply {
            rv.setHasFixedSize(true)
            rv.layoutManager = LinearLayoutManager(activity)
            rv.adapter = adapterList
        }

        retrieveDataStatusProposal()
        retrieveDataBranch()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retrieveDataStatusProposal(){
        masterDataStatusProposalViewModel.getStatusProposalItem.observeEvent(this) { resultObserve ->
            resultObserve.observe(viewLifecycleOwner, { result ->
                if (result != null) {
                    when (result.status) {
                        Result.Status.LOADING -> {
                            //binding.progressBar.visibility = View.VISIBLE
                            Timber.d("###-- Loading get status proposal")
                        }
                        Result.Status.SUCCESS -> {
                            //binding.progressBar.visibility = View.GONE
                            listStatusProposalItem = result.data?.data
                            initComponentStatusProposal()
                            Timber.d("###-- Success get status proposal")
                        }
                        Result.Status.ERROR -> {
                            //binding.progressBar.visibility = View.GONE
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
                            //binding.progressBar.visibility = View.VISIBLE
                            Timber.d("###-- Loading get Branch")
                        }
                        Result.Status.SUCCESS -> {
                            //binding.progressBar.visibility = View.GONE
                            listBranchItem = result.data?.data
                            initComponentBranch()
                            Timber.d("###-- Success get Branch")
                        }
                        Result.Status.ERROR -> {
                            //binding.progressBar.visibility = View.GONE
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
                            //binding.progressBar.visibility = View.VISIBLE
                            Timber.d("###-- Loading get sub Branch")
                        }
                        Result.Status.SUCCESS -> {
                            //binding.progressBar.visibility = View.GONE
                            listSubBranchItem = result.data?.data
                            initComponentSubBranch()
                            Timber.d("###-- Success get sub Branch")
                        }
                        Result.Status.ERROR -> {
                            //binding.progressBar.visibility = View.GONE
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
        adapterList.setApprovalCallback(object : ListApprovalCallback {
            override fun onItemClicked(data: ApprovalModel) {
                when (data.type) {
                    SS -> {
                        val direction =
                            ListApprovalFragmentDirections.actionListApprovalFragmentToSuggestionSystemCreateWizard(
                                toolbarTitle = "Approve Suggestion System",
                                action = APPROVE,
                                ssNo = data.typeNo,
                                type = ""
                            )
                        requireView().findNavController().navigate(direction)
                    }
                    PI -> {
                        val direction =
                            ListApprovalFragmentDirections.actionListApprovalFragmentToProjectImprovementCreateWizard(
                                toolbarTitle = "Approve Project Improvement",
                                action = APPROVE,
                                piNo = data.typeNo,
                                type = ""
                            )
                        requireView().findNavController().navigate(direction)
                    }
                    CP -> {
                        val direction =
                            ListApprovalFragmentDirections.actionListApprovalFragmentToChangePointCreateWizard(
                                toolbarTitle = "Approve Change Point",
                                action = APPROVE,
                                cpNo = data.typeNo,
                            )
                        requireView().findNavController().navigate(direction)
                    }
                }
            }
        })

        listApproveViewModel.getApproval().observe(viewLifecycleOwner, {
            if (it != null) {
                adapterList.setList(it)
            }
        })
    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_black)

        setToolbar(toolbar, "List Approval")
    }

    private fun initNavigationMenu() {


        binding.apply {
            // open drawer at start
            drawerFilter.openDrawer(GravityCompat.END)

            edtFromDate.setOnClickListener {
                datePicker.showDialog(object : DatePickerCustom.Callback {
                    override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                        val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                        val mon = month + 1
                        val monthStr = if (mon < 10) "0$mon" else "$mon"
                        edtFromDate.setText("$dayStr-$monthStr-$year")
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
                        edtToDate.setText("$dayStr-$monthStr-$year")
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
                Toast.makeText(activity,  "Apply filter", Toast.LENGTH_LONG).show()
                drawerFilter.closeDrawer(GravityCompat.END)
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