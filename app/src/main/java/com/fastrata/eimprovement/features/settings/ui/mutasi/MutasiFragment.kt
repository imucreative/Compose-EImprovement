package com.fastrata.eimprovement.features.settings.ui.mutasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var userId: Int = 0
    private var roleName: String = ""
    private var limit: Int = 10
    private var page: Int = 1
    private var totalPage: Int = 1
    private var isLoading = false
    private lateinit var layoutManager: LinearLayoutManager

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

        userId = HawkUtils().getDataLogin().USER_ID
        roleName = HawkUtils().getDataLogin().ROLE_NAME

        adapter = MutasiAdapter()
        adapter.notifyDataSetChanged()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getDataListDetailSaldo()
    }

    private fun getDataListDetailSaldo() {
        try {
            adapter.clear()
            page = 1

            listMutasiModel.setMutasi(userId, limit, page)

        } catch (e: Exception){
            Timber.e("Error getDataListDetailSaldo : $e")
            Toast.makeText(requireContext(), "Error : $e", Toast.LENGTH_LONG).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        _binding = FragmentMutasiBinding.bind(view)
        initToolbar()

        binding.apply {
            layoutManager = LinearLayoutManager(activity)
            rvMutasi.setHasFixedSize(true)
            rvMutasi.layoutManager = layoutManager
            rvMutasi.adapter = adapter

            rvMutasi.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                    val total  = adapter.itemCount

                    if (!isLoading && page < totalPage){
                        if (visibleItemCount + pastVisibleItem >= total){
                            page++

                            listMutasiModel.setMutasi(userId,limit,page)

                            initComponent()
                        }
                    }

                }
            })

            swipe.setOnRefreshListener {
                swipe.isRefreshing  = true
                page = 1
                try {
                    adapter.clear()

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

    override fun onResume() {
        super.onResume()
        initComponent()
    }

    private fun initComponent() {
        try {
            isLoading = true
            listMutasiModel.getListMutasi.observeEvent(this) { resultObserve ->
                resultObserve.observe(viewLifecycleOwner, { result ->
                    if (result != null) {
                        when (result.status) {
                            Result.Status.LOADING -> {
                                HelperLoading.displayLoadingWithText(requireContext(), "", false)
                                Timber.d("###-- Loading get List CP")
                            }
                            Result.Status.SUCCESS -> {
                                HelperLoading.hideLoading()

                                val listResponse = result.data?.data
                                if (listResponse != null) {
                                    if (listResponse.isNullOrEmpty()) {
                                        binding.noDataScreen.root.visibility = VISIBLE
                                    } else {
                                        totalPage = result.data.totalPage

                                        binding.rvMutasi.visibility = VISIBLE
                                        binding.noDataScreen.root.visibility = GONE
                                        //adapter.clear()
                                        adapter.setList(listResponse)

                                        Timber.d("###-- Success get List Mutasi")
                                    }
                                }
                                isLoading = false
                            }
                            Result.Status.ERROR -> {
                                HelperLoading.hideLoading()
                                isLoading = false
                                Toast.makeText(
                                    requireContext(),
                                    "Error : ${result.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                                Timber.d("###-- Error get List Mutasi")
                            }
                        }
                    }
                })
            }
        }catch (err : Exception){
            HelperLoading.hideLoading()
            isLoading = false
            Toast.makeText(
                requireContext(),
                "Error : ${err.message}",
                Toast.LENGTH_LONG
            ).show()
            Timber.d("###-- Error get List Mutasi")
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