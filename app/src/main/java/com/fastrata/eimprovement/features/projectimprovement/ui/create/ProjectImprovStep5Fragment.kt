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
import com.fastrata.eimprovement.features.projectimprovement.data.model.AkarMasalahModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementViewModel
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import javax.inject.Inject

class ProjectImprovStep5Fragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentProjectImprovementStep5Binding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : ProjectImprovementViewModel
    private lateinit var adapter : AkarMasalahAdapter
    private var data : ProjectImprovementCreateModel? = null
    private var piNo: String? = ""
    private var action: String? = ""
    private var source: String = PI_CREATE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep5Binding.inflate(inflater, container, false)

        piNo = arguments?.getString(PI_DETAIL_DATA)
        action = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (piNo == "") PI_CREATE else PI_DETAIL_DATA

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
        viewModel.setAkarMasalah(source)

        adapter = AkarMasalahAdapter(action = action, clickedItemListener = { akarMasalahItem, index ->
            changeItemListener(akarMasalahItem, index)
        })
        adapter.notifyDataSetChanged()

        binding.apply {
            rvAkarMasalah.setHasFixedSize(true)
            rvAkarMasalah.layoutManager = LinearLayoutManager(context)
            rvAkarMasalah.adapter = adapter
        }

        adapter.setAkarMslhCallback(object : AkarMasalahCallback {
            override fun onItemClicked(data: AkarMasalahModel) {

            }
        })

        viewModel.getAkarMasalah().observe(viewLifecycleOwner,{
            if(it != null){
                adapter.setList(it)
            }
        })
    }

    private fun changeItemListener(akarMasalahModel: AkarMasalahModel, index: Int) {
        viewModel.updateAkarMasalah(akarMasalahModel, index, source)
    }

    private fun setValidation() {
        (activity as ProjectImprovementCreateWizard).setPiCreateCallback(object : ProjectImprovementSystemCreateCallback {
            override fun onDataPass(): Boolean {
                var stat: Boolean
                data = HawkUtils().getTempDataCreatePi(source)
                binding.apply {
                    stat = if (data?.akarMasalah?.size == 0) {
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            resources.getString(R.string.problem_suggest),
                            R.drawable.ic_close, R.color.red_500)
                        false
                    } else {
                        HawkUtils().setTempDataCreatePi(
                            id = data?.id,
                            piNo = data?.piNo,
                            date = data?.date,
                            title = data?.title,
                            branch = data?.branch,
                            subBranch = data?.subBranch,
                            department = data?.department,
                            years = data?.years,
                            statusImplementationModel = data?.statusImplementationModel,
                            identification = data?.identification,
                            target = data?.target,
                            sebabMasalah = data?.sebabMasalah,
                            akarMasalah = data?.akarMasalah,
                            nilaiOutput = data?.nilaiOutput,
                            nqiModel = data?.nqiModel,
                            teamMember = data?.teamMember,
                            categoryFixing = data?.categoryFixing,
                            hasilImplementasi = data?.implementationResult,
                            attachment = data?.attachment,
                            statusProposal = data?.statusProposal,
                            headId = data?.headId,
                            userId = data?.userId,
                            orgId = data?.orgId,
                            warehouseId = data?.warehouseId,
                            historyApproval = data?.historyApproval,
                            activityType = data?.activityType,
                            submitType = data?.submitType,
                            comment = data?.comment,
                            source = source
                        )
                        true
                    }
                }

                return stat
            }
        })
    }
}