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
import com.fastrata.eimprovement.utils.HawkUtils
import android.widget.ArrayAdapter
import com.fastrata.eimprovement.features.suggestionsystem.data.model.*
import com.fastrata.eimprovement.utils.DataDummySs
import com.fastrata.eimprovement.utils.Tools.hideKeyboard
import android.widget.AdapterView.OnItemClickListener
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentSuggestionSystemStep2Binding
import com.fastrata.eimprovement.utils.SnackBarCustom
import timber.log.Timber

class SuggestionSystemStep3Fragment: Fragment() {

    private var _binding: FragmentSuggestionSystemStep3Binding? = null
    private val binding get() = _binding!!
    private var data: SuggestionSystemCreateModel? = null
    private lateinit var viewModel: SsCreateTeamMemberViewModel
    private lateinit var adapter: SsCreateTeamMemberAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuggestionSystemStep3Binding.inflate(inflater, container, false)
        data = HawkUtils().getTempDataCreateSs()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSuggestionSystemStep3Binding.bind(view)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SsCreateTeamMemberViewModel::class.java)

        initComponent(data?.teamMember)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initComponent(teamMember: ArrayList<TeamMemberItem?>?) {
        viewModel.setSuggestionSystemTeamMember()
        adapter = SsCreateTeamMemberAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvSsTeamMember.setHasFixedSize(true)
            rvSsTeamMember.layoutManager = LinearLayoutManager(context)
            rvSsTeamMember.adapter = adapter

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

        adapter.ssCreateCallback(object : SuggestionSystemCreateTeamMemberCallback {
            override fun removeClicked(data: TeamMemberItem) {
                Toast.makeText(context, data.name, Toast.LENGTH_LONG).show()

                teamMember?.remove(data)

                viewModel.updateTeamMember(teamMember)
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
                            root.rootView, layoutInflater, resources, root.rootView.context,
                            "Name must be fill before added",
                            R.drawable.ic_close, R.color.red_500)
                        memberName.requestFocus()

                    }
                    department.isEmpty() -> {
                        SnackBarCustom.snackBarIconInfo(
                            root.rootView, layoutInflater, resources, root.rootView.context,
                            "Department must be fill before added",
                            R.drawable.ic_close, R.color.red_500)
                        memberDepartment.requestFocus()
                    }
                    task.isEmpty() -> {
                        SnackBarCustom.snackBarIconInfo(
                            root.rootView, layoutInflater, resources, root.rootView.context,
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
        (activity as SuggestionSystemCreateWizard).setSsCreateCallback(object : SuggestionSystemCreateCallback {
            override fun onDataPass(): Boolean {
                var stat: Boolean

                binding.apply {
                    stat = if (data?.teamMember?.size == 0) {
                        SnackBarCustom.snackBarIconInfo(
                            root.rootView, layoutInflater, resources, root.rootView.context,
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