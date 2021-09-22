package com.fastrata.eimprovement.features.approval.ui

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
import com.fastrata.eimprovement.databinding.FragmentListApprovalBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.approval.data.model.ApprovalModel
import com.fastrata.eimprovement.ui.setToolbar
import com.fastrata.eimprovement.utils.APPROVE
import com.fastrata.eimprovement.utils.DatePickerCustom
import com.fastrata.eimprovement.utils.SS
import timber.log.Timber
import javax.inject.Inject

class ListApprovalFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentListApprovalBinding
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var viewModelList: ListApprovalViewModel
    private lateinit var adapterList: ListApprovalAdapter
    private lateinit var datePicker: DatePickerCustom

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListApprovalBinding.inflate(inflater, container, false)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        context ?: return binding.root

        viewModelList = injectViewModel(viewModelFactory)

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
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_black)

        setToolbar(toolbar, "List Approval")
    }

    private fun initComponent(activity: FragmentActivity) {
        viewModelList.setApproval()
        adapterList = ListApprovalAdapter()
        adapterList.notifyDataSetChanged()

        binding.apply {
            rvAppr.setHasFixedSize(true)
            rvAppr.layoutManager = LinearLayoutManager(activity)
            rvAppr.adapter = adapterList
        }

        adapterList.setApprovalCallback(object : ListApprovalCallback {
            override fun onItemClicked(data: ApprovalModel) {
                if (data.type == SS) {
                    val direction =
                        ListApprovalFragmentDirections.actionListApprovalFragmentToSuggestionSystemCreateWizard(
                            toolbarTitle = "Approve Suggestion System",
                            action = APPROVE,
                            ssNo = data.ssNo,
                        )
                    requireView().findNavController().navigate(direction)
                }
            }
        })

        viewModelList.getApproval().observe(viewLifecycleOwner, {
            if (it != null) {
                adapterList.setList(it)
            }
        })
    }

    private fun initNavigationMenu() {

        binding.apply {

            val drawer = drawerFilter

            // open drawer at start
            drawer.openDrawer(GravityCompat.END)

            filterStartDate.setOnClickListener {
                datePicker.showDialog(object : DatePickerCustom.Callback {
                    override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                        val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                        val mon = month + 1
                        val monthStr = if (mon < 10) "0$mon" else "$mon"
                        edtStartDate.text = "$dayStr-$monthStr-$year"
                    }
                })
            }

            filterEndDate.setOnClickListener {
                datePicker.showDialog(object : DatePickerCustom.Callback {
                    override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                        val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                        val mon = month + 1
                        val monthStr = if (mon < 10) "0$mon" else "$mon"
                        edtEndDate.text = "$dayStr-$monthStr-$year"
                    }
                })
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