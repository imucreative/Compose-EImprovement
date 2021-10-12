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
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.databinding.FragmentSuggestionSystemBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemModel
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposalItem
import com.fastrata.eimprovement.featuresglobal.viewmodel.StatusProposalViewModel
import com.fastrata.eimprovement.ui.setToolbar
import com.fastrata.eimprovement.utils.ADD
import com.fastrata.eimprovement.utils.DatePickerCustom
import com.fastrata.eimprovement.utils.EDIT
import com.fastrata.eimprovement.utils.Tools.hideKeyboard
import com.fastrata.eimprovement.utils.observeEvent
import timber.log.Timber
import javax.inject.Inject

class SuggestionSystemFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentSuggestionSystemBinding? = null
    private val binding get() = _binding!!
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var listSsViewModel: SuggestionSystemViewModel
    private lateinit var masterDataStatusProposalViewModel: StatusProposalViewModel
    private lateinit var adapter: SuggestionSystemAdapter
    private lateinit var datePicker: DatePickerCustom
    private var listStatusProposalItem: List<StatusProposalItem>? = null
    private lateinit var selectedStatusProposal: StatusProposalItem

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

        datePicker = DatePickerCustom(
            context = binding.root.context, themeDark = true,
            minDateIsCurrentDate = true, parentFragmentManager
        )

        listSsViewModel.setSuggestionSystem()

        masterDataStatusProposalViewModel.setStatusProposal()

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

        binding.apply {
            rvSs.setHasFixedSize(true)
            rvSs.layoutManager = LinearLayoutManager(activity)
            rvSs.adapter = adapter

            create.setOnClickListener {
                val direction = SuggestionSystemFragmentDirections.actionSuggestionSystemFragmentToSuggestionSystemCreateWizard(
                    toolbarTitle = "Create Suggestion System", action = ADD, ssNo = "", type = ""
                )
                it.findNavController().navigate(direction)
            }
        }

        retrieveDataStatusProposal()
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
                            Timber.d("###-- Loading get team member name")
                        }
                        Result.Status.SUCCESS -> {
                            //binding.progressBar.visibility = View.GONE
                            listStatusProposalItem = result.data?.data
                            initComponentStatusProposal()
                            Timber.d("###-- Success get team member name")
                        }
                        Result.Status.ERROR -> {
                            //binding.progressBar.visibility = View.GONE
                            Timber.d("###-- Error get team member name")
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

    private fun initComponent() {
        adapter.setSuggestionSystemCallback(object : SuggestionSystemCallback{
            override fun onItemClicked(data: SuggestionSystemModel) {
                val direction = SuggestionSystemFragmentDirections.actionSuggestionSystemFragmentToSuggestionSystemCreateWizard(
                    toolbarTitle = "Edit Suggestion System", action = EDIT, ssNo = data.ssNo, type = ""
                )
                requireView().findNavController().navigate(direction)
            }
        })

        listSsViewModel.getSuggestionSystem().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
            }
        })
    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_black)

        setToolbar(toolbar, "Suggestion System (SS)")
    }

    private fun initNavigationMenu() {

        binding.apply {

            val drawer = drawerFilter

            // open drawer at start
            drawer.openDrawer(GravityCompat.END)

            edtFromDate.setOnClickListener {
                datePicker.showDialog(object : DatePickerCustom.Callback {
                    override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                        val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                        val mon = month + 1
                        val monthStr = if (mon < 10) "0$mon" else "$mon"
                        edtFromDate.setText("$dayStr-$monthStr-$year")
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
                    }
                })
            }

            btnCloseFilter.setOnClickListener {
                drawer.closeDrawer(GravityCompat.END)
            }

            btnApply.setOnClickListener {
                Toast.makeText(context,  "Apply filter", Toast.LENGTH_LONG).show()
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