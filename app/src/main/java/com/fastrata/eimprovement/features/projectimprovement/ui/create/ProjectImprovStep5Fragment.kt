package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep5Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.projectimprovement.adapter.AkarMasalahAdapter
import com.fastrata.eimprovement.features.projectimprovement.callback.AkarMasalahCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.AkarMasalahItem
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementViewModel
import com.fastrata.eimprovement.utils.HawkUtils
import javax.inject.Inject

class ProjectImprovStep5Fragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var _binding: FragmentProjectImprovementStep5Binding
    private val binding get() = _binding
    private lateinit var viewModel : ProjectImprovementViewModel
    private lateinit var adapter : AkarMasalahAdapter
    private var data : ProjectImprovementCreateModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep5Binding.inflate(layoutInflater, container, false)
        data = HawkUtils().getTempDataCreatePi()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep5Binding.bind(view)
        viewModel = injectViewModel(viewModelFactory)
        //viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ProjectImprovementViewModel::class.java)

        initComponent()
    }

    private fun initComponent() {
        viewModel.setAkarMasalah()
        adapter = AkarMasalahAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvAkarMasalah.setHasFixedSize(true)
            rvAkarMasalah.layoutManager = LinearLayoutManager(context)
            rvAkarMasalah.adapter = adapter
        }

        adapter.setAkarMslhCallback(object : AkarMasalahCallback {
            override fun onItemClicked(data: AkarMasalahItem) {
                Toast.makeText(context,data.akarmslsh, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.getAkarMasalah().observe(viewLifecycleOwner,{
            if(it != null){
                adapter.setList(it)
            }
        })
    }


}