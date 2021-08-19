package com.fastrata.eimprovement.features.ChangesPoint.ui.create

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.databinding.FragmentChangesPointStep2Binding
import com.fastrata.eimprovement.features.ChangesPoint.data.model.ChangePointRewardModel
import com.fastrata.eimprovement.features.ChangesPoint.ui.create.add.AddChangeRewardActivity

class ChangesPointStep2Fragment: Fragment() {

    private lateinit var _binding:FragmentChangesPointStep2Binding
    private lateinit var viewModel:ChangePointRewardModelView
    private lateinit var adapter:ChangesRewardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangesPointStep2Binding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            ChangePointRewardModelView::class.java)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentChangesPointStep2Binding.bind(view)
        initComponent()
    }

    private fun initComponent() {
        viewModel.setChangeRewardPoint()
        adapter = ChangesRewardAdapter()
        adapter.notifyDataSetChanged()

        _binding.apply {
            rvChangepoint.setHasFixedSize(true)
            rvChangepoint.layoutManager = LinearLayoutManager(context)
            rvChangepoint.adapter = adapter

            addPoint.setOnClickListener {
                Intent(context, AddChangeRewardActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

        adapter.setChangeRewardCallback(object  : ChangeRewardCallback{
            override fun removeClicked(data: ChangePointRewardModel) {
                Toast.makeText(context, data.hadiah, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.getChangeRewardPoint().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
            }
        })


    }

}