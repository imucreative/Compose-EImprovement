package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep7Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectImprovementSystemCreateCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementViewModel
import com.fastrata.eimprovement.ui.adapter.*
import com.fastrata.eimprovement.ui.model.*
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.Tools.hideKeyboard
import timber.log.Timber
import javax.inject.Inject

class ProjectImprovStep7Fragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentProjectImprovementStep7Binding? = null
    private val binding get() = _binding!!
    private var data: ProjectImprovementCreateModel? = null
    private var piNo: String? = ""
    private var action: String? = ""
    private lateinit var viewModelTeamMember: ProjectImprovementViewModel
    private lateinit var teamMemberAdapter: TeamMemberAdapter
    private lateinit var listMemberItem: List<MemberNameItem>
    private lateinit var listDepartmentItem: List<MemberDepartmentItem>
    private lateinit var listTaskItem: List<MemberTaskItem>
    private lateinit var selectedMember: MemberNameItem
    private lateinit var selectedDepartment: MemberDepartmentItem
    private lateinit var selectedTask: MemberTaskItem
    private var source: String = PI_CREATE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep7Binding.inflate(layoutInflater, container, false)
        viewModelTeamMember = injectViewModel(viewModelFactory)

        piNo = arguments?.getString(PI_DETAIL_DATA)
        action = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (piNo == "") PI_CREATE else PI_DETAIL_DATA

        data = HawkUtils().getTempDataCreatePi(source)
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

        _binding = FragmentProjectImprovementStep7Binding.bind(view)

        binding.apply {
            rvSsTeamMember.setHasFixedSize(true)
            rvSsTeamMember.layoutManager = LinearLayoutManager(context)
            rvSsTeamMember.adapter = teamMemberAdapter
        }

        initComponent()

        initList(data?.teamMember)

        setData()
        setValidation()

        if (action == APPROVE) {
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

    fun initComponent(){
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
                if (action != APPROVE) {
                    teamMember?.remove(data)

                    viewModelTeamMember.updateTeamMember(teamMember, source)
                    viewModelTeamMember.getSuggestionSystemTeamMember()
                        .observe(viewLifecycleOwner, {
                            if (it != null) {
                                teamMemberAdapter.setListTeamMember(it)
                                Timber.i("### ambil dari getSuggestionSystemTeamMember $it")
                            }
                        })
                }
            }
        })

        viewModelTeamMember.getSuggestionSystemTeamMember().observe(viewLifecycleOwner, {
            if (it != null) {
                teamMemberAdapter.setListTeamMember(it)
                Timber.i("### ambil dari getSuggestionSystemTeamMember $it")
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
                            resources.getString(R.string.name_empty),
                            R.drawable.ic_close, R.color.red_500)
                        memberName.requestFocus()

                    }
                    department.isEmpty() -> {
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            resources.getString(R.string.department_empty),
                            R.drawable.ic_close, R.color.red_500)
                        memberDepartment.requestFocus()
                    }
                    task.isEmpty() -> {
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            resources.getString(R.string.task_empty),
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

                        viewModelTeamMember.addTeamMember(addData, data?.teamMember, source)

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
        (activity as ProjectImprovementCreateWizard).setPiCreateCallback(object  :
            ProjectImprovementSystemCreateCallback{
            override fun onDataPass(): Boolean {
                binding.apply {
                val stat = if (data?.teamMember?.size == 0) {
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            resources.getString(R.string.team_member_empty),
                            R.drawable.ic_close, R.color.red_500)
                        false
                    } else {
                        HawkUtils().setTempDataCreatePi(
                            id = data?.id,
                            piNo = data?.piNo,
                            date = data?.date,
                            title = data?.title,
                            branch = data?.branch,
                            subBranch = data?.subBranch,
                            department = data?.department,
                            years = data?.years,
                            statusImplementation = data?.statusImplementation,
                            identification = data?.identification,
                            target = data?.target,
                            sebabMasalah = data?.sebabMasalah,
                            akarMasalah = data?.akarMasalah,
                            nilaiOutput = data?.nilaiOutput,
                            nqi = data?.nqi,
                            teamMember = data?.teamMember,
                            categoryFixing = data?.categoryFixing,
                            hasilImplementasi = data?.implementationResult,
                            attachment = data?.attachment,
                            statusProposal = data?.statusProposal,
                            source = source
                        )
                        true
                    }
                    return stat
                }
            }
            }
        )
    }
}