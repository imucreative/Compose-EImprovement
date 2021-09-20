package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.databinding.FragmentSuggestionSystemStep3Binding
import android.widget.ArrayAdapter
import com.fastrata.eimprovement.features.suggestionsystem.data.model.*
import com.fastrata.eimprovement.utils.Tools.hideKeyboard
import android.widget.AdapterView.OnItemClickListener
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import javax.inject.Inject

class SuggestionSystemStep3Fragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentSuggestionSystemStep3Binding? = null
    private val binding get() = _binding!!
    private var data: SuggestionSystemCreateModel? = null
    private var detailData: SuggestionSystemModel? = null
    private lateinit var viewModelTeamMember: SsCreateTeamMemberViewModel
    private lateinit var adapter: SsCreateTeamMemberAdapter
    private var source: String = SS_CREATE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuggestionSystemStep3Binding.inflate(inflater, container, false)
        viewModelTeamMember = injectViewModel(viewModelFactory)

        detailData = arguments?.getParcelable(SS_DETAIL_DATA)

        source = if (detailData == null) SS_CREATE else SS_DETAIL_DATA

        data = HawkUtils().getTempDataCreateSs(source)
        viewModelTeamMember.setSuggestionSystemTeamMember(source)

        adapter = SsCreateTeamMemberAdapter()
        adapter.notifyDataSetChanged()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSuggestionSystemStep3Binding.bind(view)
        //viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SsCreateTeamMemberViewModel::class.java)

        binding.apply {
            rvSsTeamMember.setHasFixedSize(true)
            rvSsTeamMember.layoutManager = LinearLayoutManager(context)
            rvSsTeamMember.adapter = adapter
        }

        initComponent()

        initList(data?.teamMember)

        setData()
        setValidation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initComponent() {
        binding.apply {
            val listMemberName: List<MemberNameItem> = DataDummySs.generateDummyNameMember()
            val adapterMemberName = ArrayAdapter(
                requireContext(), android.R.layout.simple_list_item_1,
                listMemberName.map { value ->
                    value.name
                }
            )
            memberName.setAdapter(adapterMemberName)
            memberName.onItemClickListener = onItemClickListener

            val listMemberDepartment: List<MemberDepartmentItem> = DataDummySs.generateDummyDepartmentMember()
            val adapterMemberDepartment = ArrayAdapter(
                requireContext(), android.R.layout.simple_list_item_1,
                listMemberDepartment.map { value ->
                    value.department
                }
            )
            memberDepartment.setAdapter(adapterMemberDepartment)
            memberDepartment.onItemClickListener = onItemClickListener

            val listMemberTask: List<MemberTaskItem> = DataDummySs.generateDummyTaskMember()
            val adapterMemberTask = ArrayAdapter(
                requireContext(), android.R.layout.simple_list_item_1,
                listMemberTask.map { value ->
                    value.task
                }
            )
            memberTask.setAdapter(adapterMemberTask)
            memberTask.onItemClickListener = onItemClickListener

        }
    }

    private fun initList(teamMember: ArrayList<TeamMemberItem?>?) {
        adapter.ssCreateCallback(object : SuggestionSystemCreateTeamMemberCallback {
            override fun removeClicked(data: TeamMemberItem) {
                Toast.makeText(context, data.name, Toast.LENGTH_LONG).show()

                teamMember?.remove(data)

                viewModelTeamMember.updateTeamMember(teamMember)
                viewModelTeamMember.getSuggestionSystemTeamMember().observe(viewLifecycleOwner, {
                    if (it != null) {
                        adapter.setListTeamMember(it)
                    }
                })
            }
        })

        viewModelTeamMember.getSuggestionSystemTeamMember().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setListTeamMember(it)
            }
        })
    }

    private val onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
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
                            root, layoutInflater, resources, root.context,
                            "Name must be fill before added",
                            R.drawable.ic_close, R.color.red_500)
                        memberName.requestFocus()

                    }
                    department.isEmpty() -> {
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            "Department must be fill before added",
                            R.drawable.ic_close, R.color.red_500)
                        memberDepartment.requestFocus()
                    }
                    task.isEmpty() -> {
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            "Task must be fill before added",
                            R.drawable.ic_close, R.color.red_500)
                        memberTask.requestFocus()
                    }
                    else -> {
                        val addData = TeamMemberItem(
                            name = name,
                            department = department,
                            task = task
                        )

                        viewModelTeamMember.addTeamMember(addData, data?.teamMember)

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
        (activity as SuggestionSystemCreateWizard).setSsCreateCallback(object : SuggestionSystemCreateCallback {
            override fun onDataPass(): Boolean {
                var stat: Boolean

                binding.apply {
                    stat = if (data?.teamMember?.size == 0) {
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            "Team Member must be fill before next",
                            R.drawable.ic_close, R.color.red_500)
                        false
                    } else {
                        true
                    }
                }

                return stat
            }
        })
    }
}