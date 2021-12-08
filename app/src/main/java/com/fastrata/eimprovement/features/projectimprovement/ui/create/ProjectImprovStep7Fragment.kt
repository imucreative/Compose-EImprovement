package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.R.layout.simple_list_item_1
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.featuresglobal.data.model.MemberDepartmentItem
import com.fastrata.eimprovement.featuresglobal.data.model.MemberNameItem
import com.fastrata.eimprovement.featuresglobal.data.model.MemberTaskItem
import com.fastrata.eimprovement.featuresglobal.data.model.TeamMemberItem
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep7Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectImprovementSystemCreateCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementViewModel
import com.fastrata.eimprovement.featuresglobal.adapter.*
import com.fastrata.eimprovement.featuresglobal.viewmodel.TeamMemberViewModel
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
    private lateinit var listTeamMemberViewModel: ProjectImprovementViewModel
    private lateinit var masterDataTeamMemberViewModel: TeamMemberViewModel
    private lateinit var teamMemberAdapter: TeamMemberAdapter
    private var listMemberItem: List<MemberNameItem>? = null
    private var listDepartmentItem: List<MemberDepartmentItem>? = null
    private var listTaskItem: List<MemberTaskItem>? = null
    private lateinit var selectedMember: MemberNameItem
    private lateinit var selectedDepartment: MemberDepartmentItem
    private lateinit var selectedTask: MemberTaskItem
    private var source: String = PI_CREATE
    private var departmentId: Int? = 0
    private var orgId: Int? = 0
    private var warehouseId: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep7Binding.inflate(layoutInflater, container, false)
        listTeamMemberViewModel = injectViewModel(viewModelFactory)
        masterDataTeamMemberViewModel = injectViewModel(viewModelFactory)

        piNo = arguments?.getString(PI_DETAIL_DATA)
        action = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (piNo == "") PI_CREATE else PI_DETAIL_DATA

        data = HawkUtils().getTempDataCreatePi(source)
        listTeamMemberViewModel.setSuggestionSystemTeamMember(source)

        departmentId = HawkUtils().getDataLogin().DEPARTMENT_ID
        orgId = HawkUtils().getDataLogin().ORG_ID
        warehouseId = HawkUtils().getDataLogin().WAREHOUSE_ID
        val proposalType = PI

        masterDataTeamMemberViewModel.setDepartment(
            departmentId!!, orgId!!, warehouseId!!, proposalType
        )
        masterDataTeamMemberViewModel.setTeamRole()

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

        retrieveDataMemberName()
        retrieveDataDepartment()
        retrieveDataTeamRole()

        initList(data?.teamMember)

        setData()
        setValidation()

        when (action){
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
                            Timber.d("###-- Loading get team member name")
                        }
                        Result.Status.SUCCESS -> {
                            listMemberItem = result.data?.data
                            initComponentMemberName()
                            Timber.d("###-- Success get team member name")
                        }
                        Result.Status.ERROR -> {
                            HelperLoading.hideLoading()
                            Toast.makeText(requireContext(),"Error : ${result.message}", Toast.LENGTH_LONG).show()
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
                            Timber.d("###-- Loading get Department")
                        }
                        Result.Status.SUCCESS -> {
                            listDepartmentItem = result.data?.data
                            initComponentDepartment()
                            Timber.d("###-- Success get Department")
                        }
                        Result.Status.ERROR -> {
                            HelperLoading.hideLoading()
                            Toast.makeText(requireContext(),"Error : ${result.message}", Toast.LENGTH_LONG).show()
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
                            Timber.d("###-- Loading get Team role")
                        }
                        Result.Status.SUCCESS -> {
                            listTaskItem = result.data?.data
                            initComponentTeamRole()
                            Timber.d("###-- Success get Team role")
                        }
                        Result.Status.ERROR -> {
                            HelperLoading.hideLoading()
                            Toast.makeText(requireContext(),"Error : ${result.message}", Toast.LENGTH_LONG).show()
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
        teamMemberAdapter.teamMemberCreateCallback(object : TeamMemberCallback {
            override fun removeClicked(data: TeamMemberItem) {
                if ((action != APPROVE) && (action != DETAIL)) {
                    if (!conditionImplementation()) {
                        teamMember?.remove(data)

                        listTeamMemberViewModel.updateTeamMember(teamMember, source)
                        listTeamMemberViewModel.getSuggestionSystemTeamMember().observe(viewLifecycleOwner, {
                            if (it != null) {
                                teamMemberAdapter.setListTeamMember(it)
                                Timber.i("### ambil dari getSuggestionSystemTeamMember $it")
                            }
                        })
                    }
                }
            }
        })

        listTeamMemberViewModel.getSuggestionSystemTeamMember().observe(viewLifecycleOwner, {
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
//                    data?.teamMember?.size.isGreaterThan(1) ->{
//                        SnackBarCustom.snackBarIconInfo(
//                            root, layoutInflater, resources, root.context,
//                            resources.getString(R.string.maximal_team),
//                            R.drawable.ic_close, R.color.red_500)
//                        memberTask.requestFocus()
//                    }
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
                            statusImplementationModel = data?.statusImplementationModel,
                            identification = data?.identification,
                            target = data?.target,
                            sebabMasalah = data?.sebabMasalah,
                            akarMasalah = data?.akarMasalah,
                            nilaiOutput = data?.nilaiOutput,
                            nqiModel = data?.nqiModel,
                            teamMember = data?.teamMember,
                            categoryFixing = data?.categoryFixing,
                            hasilImplementasi = data?.implementationResult,
                            attachment = data?.attachment,
                            statusProposal = data?.statusProposal,
                            headId = data?.headId,
                            userId = data?.userId,
                            orgId = data?.orgId,
                            warehouseId = data?.warehouseId,
                            historyApproval = data?.historyApproval,
                            activityType = data?.activityType,
                            submitType = data?.submitType,
                            comment = data?.comment,
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