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
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SuggestionSystemCreateCallback
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SuggestionSystemCreateWizard
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import timber.log.Timber
import javax.inject.Inject

class ListApprovalHistoryStatusSsFragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentListApprovalHistoryStatusBinding? = null
    private val binding get() = _binding!!
    private var data: SuggestionSystemCreateModel? = null
    private var typeNo: String? = ""
    private var action: String? = ""
    private lateinit var viewModelHistoryStatus: ListApprovalViewModel
    private lateinit var adapter: ListApprovalHistoryStatusAdapter
    private var source: String = SS_CREATE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListApprovalHistoryStatusBinding.inflate(layoutInflater, container, false)

        viewModelHistoryStatus = injectViewModel(viewModelFactory)

        typeNo = arguments?.getString(SS_DETAIL_DATA)
        action = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (typeNo == "") SS_CREATE else SS_DETAIL_DATA

        data = HawkUtils().getTempDataCreateSs(source)
        viewModelHistoryStatus.setApprovalHistoryStatus(source, SS)

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
            Toast.makeText(activity,"### error dari getApprovalHistoryStatus",Toast.LENGTH_SHORT).show()
        }
    }

    private fun setValidation() {
        (activity as SuggestionSystemCreateWizard).setSsCreateCallback(object :
            SuggestionSystemCreateCallback {
            override fun onDataPass(): Boolean {
                HawkUtils().setTempDataCreateSs(
                    ssNo = data?.ssNo,
                    date = data?.date,
                    title = data?.title,
                    listCategory = data?.categoryImprovement,
                    name = data?.name,
                    nik = data?.nik,
                    branchCode = data?.branchCode,
                    branch = data?.branch,
                    subBranch = data?.subBranch,
                    department = data?.department,
                    directMgr = data?.directMgr,
                    suggestion = data?.suggestion,
                    problem = data?.problem,
                    statusImplementation = data?.statusImplementation,
                    teamMember = data?.teamMember,
                    attachment = data?.attachment,
                    statusProposal = data?.statusProposal,
                    headId = data?.headId,
                    userId = data?.userId,
                    orgId = data?.orgId,
                    warehouseId = data?.warehouseId,
                    proses = data?.proses,
                    result = data?.result,
                    historyApproval = data?.historyApproval,
                    activityType = data?.activityType,
                    submitType = data?.submitType,
                    comment = data?.comment,
                    source = source
                )

                return true
            }
        })
    }
}