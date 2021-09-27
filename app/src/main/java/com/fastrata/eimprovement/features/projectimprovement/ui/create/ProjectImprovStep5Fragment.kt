package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep5Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.projectimprovement.adapter.AkarMasalahAdapter
import com.fastrata.eimprovement.features.projectimprovement.callback.AkarMasalahCallback
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectImprovementSystemCreateCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.AkarMasalahItem
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementViewModel
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.SnackBarCustom
import javax.inject.Inject

class ProjectImprovStep5Fragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentProjectImprovementStep5Binding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : ProjectImprovementViewModel
    private lateinit var adapter : AkarMasalahAdapter
    private var data : ProjectImprovementCreateModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep5Binding.inflate(inflater, container, false)

        data = HawkUtils().getTempDataCreatePi()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep5Binding.bind(view)
        viewModel = injectViewModel(viewModelFactory)

        initComponent()
        setValidation()
    }

    private fun initComponent() {
        viewModel.setAkarMasalah()

        adapter = AkarMasalahAdapter { akarMasalahItem, index ->
            changeItemListener(akarMasalahItem, index)
        }
        adapter.notifyDataSetChanged()

        binding.apply {
            rvAkarMasalah.setHasFixedSize(true)
            rvAkarMasalah.layoutManager = LinearLayoutManager(context)
            rvAkarMasalah.adapter = adapter
        }

        adapter.setAkarMslhCallback(object : AkarMasalahCallback {
            override fun onItemClicked(data: AkarMasalahItem) {

            }
        })

        viewModel.getAkarMasalah().observe(viewLifecycleOwner,{
            if(it != null){
                adapter.setList(it)
            }
        })
    }

    private fun changeItemListener(akarMasalahItem: AkarMasalahItem, index: Int) {
        viewModel.updateAkarMasalah(akarMasalahItem, index)
    }

    private fun setValidation() {
        (activity as ProjectImprovementCreateWizard).setPiCreateCallback(object : ProjectImprovementSystemCreateCallback {
            override fun onDataPass(): Boolean {
                var stat: Boolean

                binding.apply {
                    /*data?.suggestProblem?.forEach {
                        when {
                            it?.kenapa?.isEmpty() == true -> {
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, resources, root.context,
                                    "Akar Masalah (Why terakhir) must be fill before next",
                                    R.drawable.ic_close, R.color.red_500)
                                stat = false
                            }
                            it?.aksi?.isEmpty() == true -> {
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, resources, root.context,
                                    "Improvement yang dilakukan must be fill before next",
                                    R.drawable.ic_close, R.color.red_500)
                                stat = false
                            }
                            it?.detail_langkah?.isEmpty() == true -> {
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, resources, root.context,
                                    "Detail langkah improvement must be fill before next",
                                    R.drawable.ic_close, R.color.red_500)
                                stat = false
                            }
                            else -> {
                                viewModel.updateAkarMasalah(AkarMasalahItem(
                                    kenapa = "",
                                    aksi = "",
                                    detail_langkah = ""
                                ), data?.suggestProblem)
                                stat = true
                            }
                        }
                    }*/

                    stat = if (data?.akarMasalah?.size == 0) {
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            "Saran/ Akar Masalah must be fill before next",
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