package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.databinding.FragmentSuggestionSystemStep3Binding
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.TeamMemberItem
import com.fastrata.eimprovement.utils.HawkUtils

class SuggestionSystemStep3Fragment: Fragment() {

    private var _binding: FragmentSuggestionSystemStep3Binding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SsCreateViewModel
    private lateinit var adapter: SsCreateAdapter
    private var data: SuggestionSystemCreateModel? = null
    private var dataTeamMember: TeamMemberItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuggestionSystemStep3Binding.inflate(layoutInflater, container, false)
        data = HawkUtils().getTempDataCreateSs()
        dataTeamMember = HawkUtils().getTempTeamMember()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSuggestionSystemStep3Binding.bind(view)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SsCreateViewModel::class.java)

        initComponent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initComponent() {
        viewModel.setSuggestionSystemTeamMember()
        adapter = SsCreateAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvSsTeamMember.setHasFixedSize(true)
            rvSsTeamMember.layoutManager = LinearLayoutManager(context)
            rvSsTeamMember.adapter = adapter
        }

        adapter.suggestionSystemCreateCallback(object : SuggestionSystemCreateTeamMemberCallback {
            override fun removeClicked(data: TeamMemberItem) {
                Toast.makeText(context, data.name, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.getSuggestionSystemTeamMember().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setListTeamMember(it)
                println("### ambil dari getSuggestionSystem $it")
            }
        })

        setData(binding)
    }



    private fun setData(binding: FragmentSuggestionSystemStep3Binding) {
        binding.run {
            val name = "Budi"
            val department = "ICT"
            val task = "Anggota"

            val addData = TeamMemberItem(
                name = name,
                department = department,
                task = task
            )

            addTeamMember.setOnClickListener {
                viewModel.addTeamMember(addData)
            }
        }
    }
}