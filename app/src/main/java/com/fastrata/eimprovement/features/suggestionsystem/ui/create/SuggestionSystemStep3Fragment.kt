package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.R.layout.simple_list_item_1
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.ArrayAdapter
import com.fastrata.eimprovement.features.suggestionsystem.data.model.*
import com.fastrata.eimprovement.utils.Tools.hideKeyboard
import android.widget.AdapterView.OnItemClickListener
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.databinding.FragmentSuggestionSystemStep3Binding
import com.fastrata.eimprovement.featuresglobal.data.model.MemberDepartmentItem
import com.fastrata.eimprovement.featuresglobal.data.model.MemberNameItem
import com.fastrata.eimprovement.featuresglobal.data.model.MemberTaskItem
import com.fastrata.eimprovement.featuresglobal.data.model.TeamMemberItem
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.featuresglobal.adapter.*
import com.fastrata.eimprovement.featuresglobal.viewmodel.TeamMemberViewModel
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
    private lateinit var listTeamMemberViewModel: SsCreateTeamMemberViewModel
    private lateinit var masterDataTeamMemberViewModel: TeamMemberViewModel
    private lateinit var teamMemberAdapter: TeamMemberAdapter
    private var listMemberItem: List<MemberNameItem>? = null
    private var listDepartmentItem: List<MemberDepartmentItem>? = null
    private var listTaskItem: List<MemberTaskItem>? = null
    private lateinit var selectedMember: MemberNameItem
    private lateinit var selectedDepartment: MemberDepartmentItem
    private lateinit var selectedTask: MemberTaskItem
    private var source: String = SS_CREATE
    private var departmentId: Int? = 0
    private var orgId: Int? = 0
    private var warehouseId: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuggestionSystemStep3Binding.inflate(inflater, container, false)
        listTeamMemberViewModel = injectViewModel(viewModelFactory)
        masterDataTeamMemberViewModel = injectViewModel(viewModelFactory)

        ssNo = arguments?.getString(SS_DETAIL_DATA)
        ssAction = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (ssNo == "") SS_CREATE else SS_DETAIL_DATA

        data = HawkUtils().getTempDataCreateSs(source)
        listTeamMemberViewModel.setSuggestionSystemTeamMember(source)

        departmentId = HawkUtils().getDataLogin().DEPARTMENT_ID
        orgId = HawkUtils().getDataLogin().ORG_ID
        warehouseId = HawkUtils().getDataLogin().WAREHOUSE_ID
        val proposalType = SS

        masterDataTeamMemberViewModel.setTeamRole()
        masterDataTeamMemberViewModel.setDepartment(
            departmentId!!, orgId!!, warehouseId!!, proposalType
        )

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

        retrieveDataMemberName()
        retrieveDataDepartment()
        retrieveDataTeamRole()

        initList(data?.teamMember)

        setData()
        setValidation()

        when (ssAction){
            APPROVE, DETAIL -> disableForm()
        }

        when {
            conditionImplementation() -> disableForm()
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

    private fun conditionImplementation(): Boolean {
        return when (data?.statusProposal?.id) {
            6, 9 -> {
                true
            }
            else -> {
                false
            }
        }
    }

    private fun retrieveDataMemberName(){
        masterDataTeamMemberViewModel.getTeamMemberName.observeEvent(this) { resultObserve ->
            resultObserve.observe(viewLifecycleOwner, { result ->
                if (result != null) {
                    when (result.status) {
                        Result.Status.LOADING -> {
                            binding.memberName.isEnabled = false
                            Timber.d("###-- Loading get team member name")
                        }
                        Result.Status.SUCCESS -> {
                            binding.memberName.isEnabled = true
                            listMemberItem = result.data?.data
                            initComponentMemberName()
                            Timber.d("###-- Success get team member name")
                        }
                        Result.Status.ERROR -> {
                            binding.memberName.isEnabled = false
                            Timber.d("###-- Error get team member name")
                        }

                    }

                }
            })
        }
    }

    private fun retrieveDataDepartment(){
        masterDataTeamMemberViewModel.getDepartment.observeEvent(this) { resultObserve ->
            resultObserve.observe(viewLifecycleOwner, { result ->
                if (result != null) {
                    when (result.status) {
                        Result.Status.LOADING -> {
                            binding.memberDepartment.isEnabled = false
                            Timber.d("###-- Loading get Department")
                        }
                        Result.Status.SUCCESS -> {
                            binding.memberDepartment.isEnabled = true
                            listDepartmentItem = result.data?.data
                            initComponentDepartment()
                            Timber.d("###-- Success get Department")
                        }
                        Result.Status.ERROR -> {
                            binding.memberDepartment.isEnabled = false
                            Timber.d("###-- Error get Department")
                        }

                    }

                }
            })
        }
    }

    private fun retrieveDataTeamRole(){
        masterDataTeamMemberViewModel.getTeamRole.observeEvent(this) { resultObserve ->
            resultObserve.observe(viewLifecycleOwner, { result ->
                if (result != null) {
                    when (result.status) {
                        Result.Status.LOADING -> {
                            binding.memberTask.isEnabled = false
                            Timber.d("###-- Loading get Team role")
                        }
                        Result.Status.SUCCESS -> {
                            binding.memberTask.isEnabled = true
                            listTaskItem = result.data?.data
                            initComponentTeamRole()
                            Timber.d("###-- Success get Team role")
                        }
                        Result.Status.ERROR -> {
                            binding.memberTask.isEnabled = false
                            Timber.d("###-- Error get Team role")
                        }

                    }

                }
            })
        }
    }

    private fun initComponentMemberName() {
        binding.apply {
            val adapterMemberName = ArrayAdapter(
                requireContext(), simple_list_item_1,
                listMemberItem!!.map { value ->
                    value.name
                }
            )
            memberName.setAdapter(adapterMemberName)
            memberName.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
                selectedMember = listMemberItem!![i]
                hideKeyboard()
            }
        }
    }

    private fun initComponentDepartment() {
        binding.apply {
            val adapterMemberDepartment = ArrayAdapter(
                requireContext(), simple_list_item_1,
                listDepartmentItem!!.map { value ->
                    value.department
                }
            )
            memberDepartment.setAdapter(adapterMemberDepartment)
            memberDepartment.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
                selectedDepartment = listDepartmentItem!![i]
                memberName.setText("")
                masterDataTeamMemberViewModel.setTeamMemberName(
                    listDepartmentItem!![i].id,
                    orgId!!,
                    warehouseId!!
                )
                hideKeyboard()
            }
        }
    }

    private fun initComponentTeamRole() {
        binding.apply {
            val adapterMemberTask = ArrayAdapter(
                requireContext(), simple_list_item_1,
                listTaskItem!!.map { value ->
                    value.task
                }
            )
            memberTask.setAdapter(adapterMemberTask)
            memberTask.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
                selectedTask = listTaskItem!![i]
                hideKeyboard()
            }
        }
    }

    private fun initList(teamMember: ArrayList<TeamMemberItem?>?) {
        Timber.e(ssAction)
        teamMemberAdapter.teamMemberCreateCallback(object : TeamMemberCallback {
            override fun removeClicked(dataMember: TeamMemberItem) {
                if ((ssAction != APPROVE) && (ssAction != DETAIL)) {
                    if (!conditionImplementation()) {
                        teamMember?.remove(dataMember)

                        listTeamMemberViewModel . updateTeamMember (teamMember, source)
                        listTeamMemberViewModel.getSuggestionSystemTeamMember().observe(viewLifecycleOwner, {
                            if (it != null) {
                                teamMemberAdapter.setListTeamMember(it)
                            }
                        })
                    }
                }
            }
        })

        listTeamMemberViewModel.getSuggestionSystemTeamMember().observe(viewLifecycleOwner, {
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
                    task.isEmpty() -> {
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            resources.getString(R.string.task_empty),
                            R.drawable.ic_close, R.color.red_500)
                        memberTask.requestFocus()
                    }
                    department.isEmpty() -> {
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            resources.getString(R.string.department_empty),
                            R.drawable.ic_close, R.color.red_500)
                        memberDepartment.requestFocus()
                    }
                    name.isEmpty() -> {
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            resources.getString(R.string.name_empty),
                            R.drawable.ic_close, R.color.red_500)
                        memberName.requestFocus()
                    }
                    data?.teamMember.isNullOrEmpty() && task != "Ketua"->{
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            resources.getString(R.string.ketua_not_found),
                            R.drawable.ic_close, R.color.red_500)
                        memberTask.requestFocus()
                    }
                    data?.teamMember?.size.isGreaterThan(1) ->{
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            resources.getString(R.string.maximal_team),
                            R.drawable.ic_close, R.color.red_500)
                        memberTask.requestFocus()
                    }
                    !data?.teamMember.isNullOrEmpty() && data?.teamMember?.get(0)!!.task!!.id == 1 && task == "Ketua" ->{
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            resources.getString(R.string.maximal_ketua),
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

                        listTeamMemberViewModel.addTeamMember(addData, data?.teamMember, source)

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
                            resources.getString(R.string.team_member_empty),
                            R.drawable.ic_close, R.color.red_500)
                        false
                    } else {
                        HawkUtils().setTempDataCreateSs(
                            id = data?.id,
                            ssNo = data?.ssNo,
                            date = data?.date,
                            title = data?.title,
                            listCategory = data?.categoryImprovement,
                            name = data?.name,
                            nik = data?.nik,
                            branchCode = data?.branchCode,
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
                            headId = data?.headId,
                            userId = data?.userId,
                            orgId = data?.orgId,
                            warehouseId = data?.warehouseId,
                            proses = data?.proses,
                            result = data?.result,
                            historyApproval = data?.historyApproval,
                            activityType = data?.activityType,
                            submitType = data?.submitType,
                            comment = data?.comment,
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