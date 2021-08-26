package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep7Binding
import com.fastrata.eimprovement.features.projectimprovement.adapter.PiCreateTeamMemberAdapter
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjecImprovementCreateTeamMemberCallback
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjecImprovementSystemCreateCallback
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementViewModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.*
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.*
import com.fastrata.eimprovement.utils.DataDummySs
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.SnackBarCustom
import com.fastrata.eimprovement.utils.Tools.hideKeyboard
import timber.log.Timber

class ProjectImprovStep7Fragment : Fragment () {

    private lateinit var _binding: FragmentProjectImprovementStep7Binding
    private val binding get() = _binding
    private var data: SuggestionSystemCreateModel? = null
    private lateinit var viewModel: ProjectImprovementViewModel
    private lateinit var adapter: PiCreateTeamMemberAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep7Binding.inflate(layoutInflater, container, false)
        data = HawkUtils().getTempDataCreateSs()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep7Binding.bind(view)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            ProjectImprovementViewModel::class.java)
        initComponent()
    }

    fun initComponent(){

        viewModel.setSuggestionSystemTeamMember()
        adapter = PiCreateTeamMemberAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvSsTeamMember.setHasFixedSize(true)
            rvSsTeamMember.layoutManager = LinearLayoutManager(context)
            rvSsTeamMember.adapter = adapter

            val listMemberName: List<MemberNameItem> = DataDummySs.generateDummyNameMember()
            val adapterMemberName = ArrayAdapter(
                requireContext(), R.layout.simple_list_item_1,
                listMemberName.map { value ->
                    value.name
                }
            )
            memberName.setAdapter(adapterMemberName)
            memberName.onItemClickListener = onItemClickListener

            val listMemberDepartment: List<MemberDepartmentItem> = DataDummySs.generateDummyDepartmentMember()
            val adapterMemberDepartment = ArrayAdapter(
                requireContext(), R.layout.simple_list_item_1,
                listMemberDepartment.map { value ->
                    value.department
                }
            )
            memberDepartment.setAdapter(adapterMemberDepartment)
            memberDepartment.onItemClickListener = onItemClickListener

            val listMemberTask: List<MemberTaskItem> = DataDummySs.generateDummyTaskMember()
            val adapterMemberTask = ArrayAdapter(
                requireContext(), R.layout.simple_list_item_1,
                listMemberTask.map { value ->
                    value.task
                }
            )
            memberTask.setAdapter(adapterMemberTask)
            memberTask.onItemClickListener = onItemClickListener

            /*val adapt = context?.let {
                ListAdapterCustom(it, R.layout.item_auto_complete_text_view, DataDummySs.generateDummyNameMember())
            }
            memberName.threshold = 1
            memberName.setAdapter(adapt)
            memberName.setOnItemClickListener { adapterView, view, position, id ->
                hideKeyboard()
                val data = adapterView.getItemAtPosition(position)
                if (data is MemberNameItem){
                    Toast.makeText(context, "Clicked " + data.name, Toast.LENGTH_SHORT).show()
                }
            }*/

        }

        adapter.piCreateCallback(object : ProjecImprovementCreateTeamMemberCallback {
            override fun removeClicked(data: TeamMemberItem) {
                Toast.makeText(context, data.name, Toast.LENGTH_LONG).show()

                this@ProjectImprovStep7Fragment.data?.teamMember?.remove(data)

                viewModel.updateTeamMember(this@ProjectImprovStep7Fragment.data?.teamMember)
                viewModel.getSuggestionSystemTeamMember().observe(viewLifecycleOwner, {
                    if (it != null) {
                        adapter.setListTeamMember(it)
                        Timber.i("### ambil dari getSuggestionSystemTeamMember $it")
                    }
                })
            }
        })

        viewModel.getSuggestionSystemTeamMember().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setListTeamMember(it)
                Timber.i("### ambil dari getSuggestionSystemTeamMember $it")
            }
        })

        setData()
        setValidation()
    }

    private val onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
        hideKeyboard()
    }

    private fun setData() {
        binding.apply {
            addTeamMember.setOnClickListener {

                val name = memberName.text.toString()
                val department = memberDepartment.text.toString()
                val task = memberTask.text.toString()

                when {
                    name.isEmpty() -> {
                        SnackBarCustom.snackBarIconInfo(
                            root.rootView, layoutInflater, resources, root.rootView.context,
                            "Name must be fill before added",
                            com.fastrata.eimprovement.R.drawable.ic_close, com.fastrata.eimprovement.R.color.red_500)
                        memberName.requestFocus()

                    }
                    department.isEmpty() -> {
                        SnackBarCustom.snackBarIconInfo(
                            root.rootView, layoutInflater, resources, root.rootView.context,
                            "Department must be fill before added",
                            com.fastrata.eimprovement.R.drawable.ic_close, com.fastrata.eimprovement.R.color.red_500)
                        memberDepartment.requestFocus()
                    }
                    task.isEmpty() -> {
                        SnackBarCustom.snackBarIconInfo(
                            root.rootView, layoutInflater, resources, root.rootView.context,
                            "Task must be fill before added",
                            com.fastrata.eimprovement.R.drawable.ic_close, com.fastrata.eimprovement.R.color.red_500)
                        memberTask.requestFocus()
                    }
                    else -> {
                        val addData = TeamMemberItem(
                            name = name,
                            department = department,
                            task = task
                        )

                        viewModel.addTeamMember(addData, data?.teamMember)

                        memberName.requestFocus()
                        memberName.setText("")
                        memberDepartment.setText("")
                        memberTask.setText("")
                    }
                }
            }
        }
    }

    private fun setValidation() {
        (activity as ProjectImprovementCreateWizard).setpiCreateCallback(object  :
            ProjecImprovementSystemCreateCallback{
            override fun onDataPass(): Boolean {
                binding.apply {
                var stat = if (data?.teamMember?.size == 0) {
                        SnackBarCustom.snackBarIconInfo(
                            root.rootView, layoutInflater, resources, root.rootView.context,
                            "Team Member must be fill before next",
                            com.fastrata.eimprovement.R.drawable.ic_close, com.fastrata.eimprovement.R.color.red_500)
                        false
                    } else {
                        true
                    }
                    return stat
                }
            }
            })
    }
}