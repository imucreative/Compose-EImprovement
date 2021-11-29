package com.fastrata.eimprovement.features.settings.ui.mutasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.databinding.FragmentMutasiBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.ui.setToolbar
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.HelperLoading
import com.fastrata.eimprovement.utils.observeEvent
import timber.log.Timber
import javax.inject.Inject

class MutasiFragment : Fragment(),Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding : FragmentMutasiBinding? = null
    private val binding get() = _binding!!
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var adapter: MutasiAdapter
    private lateinit var listMutasiModel: MutasiViewCreateModel
    var userId: Int = 0
    var limit: Int = 10
    var page: Int = 1
    var roleName: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMutasiBinding.inflate(inflater,container,false)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        listMutasiModel = injectViewModel(viewModelFactory)

        context ?: return binding.root
        setHasOptionsMenu(true);

        try {
            userId = HawkUtils().getDataLogin().USER_ID
            roleName = HawkUtils().getDataLogin().ROLE_NAME
            listMutasiModel.setMutasi(userId,limit,page)
        }catch (e :Exception){
            Timber.e("Error setListCp : $e")
            Toast.makeText(requireContext(),"Error : $e", Toast.LENGTH_LONG).show()
        }

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

            swipe.setOnRefreshListener {
                swipe.isRefreshing  = true
                try {
                    listMutasiModel.setMutasi(userId,limit,page)
                    swipe.isRefreshing = false
                }catch (e: Exception){
                    Timber.e("Error setListCp : $e")
                    Toast.makeText(requireContext(),"Error : $e",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initComponent(activity: FragmentActivity) {
        listMutasiModel.getListMutasi.observeEvent(activity){resultObserve ->
            resultObserve.observe(viewLifecycleOwner,{ result ->
                if (result != null){
                    when (result.status){
                        Result.Status.LOADING -> {
                            HelperLoading.displayLoadingWithText(requireContext(),"",false)
                            Timber.d("###-- Loading get List CP")
                        }
                        Result.Status.SUCCESS -> {
                            HelperLoading.hideLoading()

                            if (result.data?.data.isNullOrEmpty()){
                                binding.rvMutasi.visibility == View.GONE
                                binding.noDataScreen.root.visibility = View.VISIBLE
                            }else{
                                binding.rvMutasi.visibility = View.VISIBLE
                                binding.noDataScreen.root.visibility = View.GONE
                                adapter.clear()
                                adapter.setList(result.data?.data!!)

                                Timber.d("###-- Success get List Mutasi")
                            }
                        }
                        Result.Status.ERROR -> {
                            HelperLoading.hideLoading()
                            Toast.makeText(requireContext(),"Error : ${result.message}", Toast.LENGTH_LONG).show()
                            Timber.d("###-- Error get List Mutasi")
                        }
                    }
                }
            })
        }
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