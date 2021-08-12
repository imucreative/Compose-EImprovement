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
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemTeamMemberModel

class SuggestionSystemStep3Fragment: Fragment() {

    private lateinit var fragment: FragmentSuggestionSystemStep3Binding
    private lateinit var viewModel: SuggestionSystemTeamMemberViewModel
    private lateinit var adapter: SuggestionSystemTeamMemberAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragment = FragmentSuggestionSystemStep3Binding.inflate(layoutInflater, container, false)
        return fragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SuggestionSystemTeamMemberViewModel::class.java)
        initComponent()
    }

    private fun initComponent() {
        viewModel.setSuggestionSystemTeamMember()
        adapter = SuggestionSystemTeamMemberAdapter()
        adapter.notifyDataSetChanged()

        fragment.apply {
            rvSsTeamMember.setHasFixedSize(true)
            rvSsTeamMember.layoutManager = LinearLayoutManager(context)
            rvSsTeamMember.adapter = adapter
        }

        adapter.setSuggestionSystemTeamMemberCallback(object : SuggestionSystemTeamMemberCallback {
            override fun removeClicked(data: SuggestionSystemTeamMemberModel) {
                Toast.makeText(context, data.name, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.getSuggestionSystemTeamMember().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
            }
        })
    }
}