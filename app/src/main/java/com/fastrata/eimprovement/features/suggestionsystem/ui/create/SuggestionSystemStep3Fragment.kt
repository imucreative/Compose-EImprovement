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
import com.fastrata.eimprovement.ui.adapter.*
import com.fastrata.eimprovement.ui.model.*
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import timber.log.Timber
import javax.inject.Inject

class SuggestionSystemStep3Fragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentSuggestionSystemStep3Binding? = null
    private val binding get() = _binding!!
    private var data: SuggestionSystemCreateModel? = null
    private var ssNo: String? = ""
    private var ssAction: String? = ""
    private lateinit var viewModelTeamMember: SsCreateTeamMemberViewModel
    private lateinit var teamMemberAdapter: TeamMemberAdapter
    private lateinit var listMemberItem: List<MemberNameItem>
    private lateinit var listDepartmentItem: List<MemberDepartmentItem>
    private lateinit var listTaskItem: List<MemberTaskItem>
    private lateinit var selectedMember: MemberNameItem
    private lateinit var selectedDepartment: MemberDepartmentItem
    private lateinit var selectedTask: MemberTaskItem
    private var source: String = SS_CREATE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuggestionSystemStep3Binding.inflate(inflater, container, false)
        viewModelTeamMember = injectViewModel(viewModelFactory)

        ssNo = arguments?.getString(SS_DETAIL_DATA)
        ssAction = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (ssNo == "") SS_CREATE else SS_DETAIL_DATA

        data = HawkUtils().getTempDataCreateSs(source)
        viewModelTeamMember.setSuggestionSystemTeamMember(source)

        listMemberItem = DataDummySs.generateDummyNameMember()
        listDepartmentItem = DataDummySs.generateDummyDepartmentMember()
        listTaskItem = DataDummySs.generateDummyTaskMember()

        teamMemberAdapter = TeamMemberAdapter()
        teamMemberAdapter.notifyDataSetChanged()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSuggestionSystemStep3Binding.bind(view)

        binding.apply {
            rvSsTeamMember.setHasFixedSize(true)
            rvSsTeamMember.layoutManager = LinearLayoutManager(context)
            rvSsTeamMember.adapter = teamMemberAdapter
        }

        initComponent()

        initList(data?.teamMember)

        setData()
        setValidation()

        if (ssAction == APPROVE) {
            disableForm()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun disableForm() {
        binding.apply {
            memberName.isEnabled = false
            memberDepartment.isEnabled = false
            memberTask.isEnabled = false

            addTeamMember.isClickable = false
        }
    }

    private fun initComponent() {
        binding.apply {
            val adapterMemberName = ArrayAdapter(
                requireContext(), android.R.layout.simple_list_item_1,
                listMemberItem.map { value ->
                    value.name
                }
            )
            memberName.setAdapter(adapterMemberName)
            memberName.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
                selectedMember = listMemberItem[i]
                hideKeyboard()
            }

            val adapterMemberDepartment = ArrayAdapter(
                requireContext(), android.R.layout.simple_list_item_1,
                listDepartmentItem.map { value ->
                    value.department
                }
            )
            memberDepartment.setAdapter(adapterMemberDepartment)
            memberDepartment.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
                selectedDepartment = listDepartmentItem[i]
                hideKeyboard()
            }

            val adapterMemberTask = ArrayAdapter(
                requireContext(), android.R.layout.simple_list_item_1,
                listTaskItem.map { value ->
                    value.task
                }
            )
            memberTask.setAdapter(adapterMemberTask)
            memberTask.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
                selectedTask = listTaskItem[i]
                hideKeyboard()
            }

        }
    }

    private fun initList(teamMember: ArrayList<TeamMemberItem?>?) {
        teamMemberAdapter.teamMemberCreateCallback(object : TeamMemberCallback {
            override fun removeClicked(data: TeamMemberItem) {
                if (ssAction != APPROVE) {
                    teamMember?.remove(data)

                    viewModelTeamMember.updateTeamMember(teamMember)
                    viewModelTeamMember.getSuggestionSystemTeamMember()
                        .observe(viewLifecycleOwner, {
                            if (it != null) {
                                teamMemberAdapter.setListTeamMember(it)
                            }
                        })
                }
            }
        })

        viewModelTeamMember.getSuggestionSystemTeamMember().observe(viewLifecycleOwner, {
            if (it != null) {
                teamMemberAdapter.setListTeamMember(it)
            }
        })
    }

    /*private val onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
        hideKeyboard()
    }*/

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
                        val memberNameObj = MemberNameItem(
                            id = selectedMember.id, name = selectedMember.name
                        )
                        val memberDepartmentObj = MemberDepartmentItem(
                            id = selectedDepartment.id, department = selectedDepartment.department
                        )
                        val memberTaskObj = MemberTaskItem(
                            id = selectedTask.id, task = selectedTask.task
                        )
                        val addData = TeamMemberItem(
                            name = memberNameObj,
                            department = memberDepartmentObj,
                            task = memberTaskObj
                        )

                        viewModelTeamMember.addTeamMember(addData, data?.teamMember)

                        memberName.requestFocus()
                        memberName.setText("")
                        memberDepartment.setText("")
                        memberTask.setText("")
                        hideKeyboard()
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
                        HawkUtils().setTempDataCreateSs(
                            ssNo = data?.ssNo,
                            date = data?.date,
                            title = data?.title,
                            listCategory = data?.categoryImprovement,
                            name = data?.name,
                            nik = data?.nik,
                            branch = data?.branch,
                            subBranch = data?.subBranch,
                            department = data?.department,
                            directMgr = data?.directMgr,
                            suggestion = data?.suggestion,
                            problem = data?.problem,
                            statusImplementation = data?.statusImplementation,
                            teamMember = data?.teamMember,
                            attachment = data?.attachment,
                            statusProposal = data?.statusProposal,
                            source = source
                        )
                        true
                    }
                }

                return stat
            }
        })
    }
}