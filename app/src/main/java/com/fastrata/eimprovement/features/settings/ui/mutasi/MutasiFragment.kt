package com.fastrata.eimprovement.features.settings.ui.mutasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentMutasiBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.ui.setToolbar
import javax.inject.Inject

class MutasiFragment : Fragment(),Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding : FragmentMutasiBinding? = null
    private val binding get() = _binding!!
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var adapter: MutasiAdapter
    private lateinit var viewCreateModel: MutasiViewCreateModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMutasiBinding.inflate(inflater,container,false)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        context ?: return binding.root

//        viewCreateModel = injectViewModel(viewModelFactory)
        viewCreateModel = MutasiViewCreateModel()

        setHasOptionsMenu(true);

        adapter = MutasiAdapter()
        adapter.notifyDataSetChanged()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        _binding = FragmentMutasiBinding.bind(view)
        initToolbar()
        initComponent(requireActivity())

        binding.apply {
            rvMutasi.setHasFixedSize(true)
            rvMutasi.layoutManager = LinearLayoutManager(activity)
            rvMutasi.adapter = adapter
        }


    }


    private fun initComponent(activity: FragmentActivity) {
        viewCreateModel.setMutasi()

        viewCreateModel.getMutasi().observe(viewLifecycleOwner,{
            if (it != null) {
                adapter.setList(it)
            }
        })
    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_black)
        setToolbar(toolbar, resources.getString(R.string.detail_balance))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                if (!findNavController().popBackStack()) activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}