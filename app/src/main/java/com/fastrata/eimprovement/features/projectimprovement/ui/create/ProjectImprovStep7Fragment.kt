package com.fastrata.eimprovement.features.projectimprovement.ui.create

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
    private var ssNo: String? = ""
    private var ssAction: String? = ""
    private lateinit var viewModelTeamMember: ProjectImprovementViewModel
    private lateinit var teamMemberAdapter: TeamMemberAdapter
    private var source: String = PI_CREATE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep7Binding.inflate(layoutInflater, container, false)
        viewModelTeamMember = injectViewModel(viewModelFactory)

        data = HawkUtils().getTempDataCreatePi()
        viewModelTeamMember.setSuggestionSystemTeamMember()

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun initComponent(){
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
        teamMemberAdapter.teamMemberCreateCallback(object : TeamMemberCallback {
            override fun removeClicked(data: TeamMemberItem) {
                Toast.makeText(context, data.name, Toast.LENGTH_LONG).show()

                teamMember?.remove(data)

                viewModelTeamMember.updateTeamMember(teamMember)
                viewModelTeamMember.getSuggestionSystemTeamMember().observe(viewLifecycleOwner, {
                    if (it != null) {
                        teamMemberAdapter.setListTeamMember(it)
                        Timber.i("### ambil dari getSuggestionSystemTeamMember $it")
                    }
                })
            }
        })

        viewModelTeamMember.getSuggestionSystemTeamMember().observe(viewLifecycleOwner, {
            if (it != null) {
                teamMemberAdapter.setListTeamMember(it)
                Timber.i("### ambil dari getSuggestionSystemTeamMember $it")
            }
        })
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
        (activity as ProjectImprovementCreateWizard).setPiCreateCallback(object  :
            ProjectImprovementSystemCreateCallback{
            override fun onDataPass(): Boolean {
                binding.apply {
                val stat = if (data?.teamMember?.size == 0) {
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            "Team Member must be fill before next",
                            R.drawable.ic_close, R.color.red_500)
                        false
                    } else {
                        true
                    }
                    return stat
                }
            }
            }
        )
    }
}