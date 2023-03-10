package com.fastrata.eimprovement.features.approval.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.databinding.FragmentListApprovalHistoryStatusBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectImprovementSystemCreateCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.features.projectimprovement.ui.create.ProjectImprovementCreateWizard
import com.fastrata.eimprovement.utils.*
import timber.log.Timber
import javax.inject.Inject

class ListApprovalHistoryStatusPiFragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentListApprovalHistoryStatusBinding? = null
    private val binding get() = _binding!!
    private var data: ProjectImprovementCreateModel? = null
    private var typeNo: String? = ""
    private var action: String? = ""
    private lateinit var viewModelHistoryStatus: ListApprovalViewModel
    private lateinit var adapter: ListApprovalHistoryStatusAdapter
    private var source: String = PI_CREATE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListApprovalHistoryStatusBinding.inflate(layoutInflater, container, false)

        viewModelHistoryStatus = injectViewModel(viewModelFactory)

        typeNo = arguments?.getString(PI_DETAIL_DATA)
        action = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (typeNo == "") PI_CREATE else PI_DETAIL_DATA

        data = HawkUtils().getTempDataCreatePi(source)
        viewModelHistoryStatus.setApprovalHistoryStatus(source, PI)

        adapter = ListApprovalHistoryStatusAdapter()
        adapter.notifyDataSetChanged()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentListApprovalHistoryStatusBinding.bind(view)

        binding.apply {
            rvHistoryStatus.setHasFixedSize(true)
            rvHistoryStatus.layoutManager = LinearLayoutManager(context)
            rvHistoryStatus.adapter = adapter
        }

        initList()
        setValidation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initList() {
        try {
            viewModelHistoryStatus.getApprovalHistoryStatus().observe(viewLifecycleOwner) {
                if (it != null) {
                    adapter.setList(it)
                    Timber.i("### ambil dari getApprovalHistoryStatus $it")
                }
            }
        }catch (err : Exception){
            Toast.makeText(activity,"### error dari getApprovalHistoryStatus", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setValidation() {
        (activity as ProjectImprovementCreateWizard).setPiCreateCallback(object:
            ProjectImprovementSystemCreateCallback{
                override fun onDataPass(): Boolean {
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
                    return true
                }
            }
        )
    }

}