package com.fastrata.eimprovement.features.approval.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.databinding.FragmentListApprovalHistoryStatusBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointCreateItemModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.utils.*
import timber.log.Timber
import javax.inject.Inject

class ListApprovalHistoryStatusCpFragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentListApprovalHistoryStatusBinding? = null
    private val binding get() = _binding!!
    private var data: ChangePointCreateItemModel? = null
    private var typeNo: String? = ""
    private var action: String? = ""
    private lateinit var viewModelHistoryStatus: ListApprovalViewModel
    private lateinit var adapter: ListApprovalHistoryStatusAdapter
    private var source: String = CP_CREATE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListApprovalHistoryStatusBinding.inflate(inflater, container, false)

        viewModelHistoryStatus = injectViewModel(viewModelFactory)

        typeNo = arguments?.getString(CP_DETAIL_DATA)
        action = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (typeNo == "") CP_CREATE else CP_DETAIL_DATA

        //data = HawkUtils().getTempDataCreateSs(source)
        viewModelHistoryStatus.setApprovalHistoryStatus(source)

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initList() {
        /*adapter.ssCreateCallback(object : SuggestionSystemCreateAttachmentCallback {
            override fun removeClicked(data: AttachmentItem) {
                if (ssAction != APPROVE) {
                    Toast.makeText(context, data.name, Toast.LENGTH_LONG).show()

                    attachment?.remove(data)

                    viewModelAttachment.updateAttachment(attachment)
                    viewModelAttachment.getSuggestionSystemAttachment()
                        .observe(viewLifecycleOwner, {
                            if (it != null) {
                                adapter.setList(it)
                                Timber.i("### ambil dari getSuggestionSystemAttachment $it")
                            }
                        })
                }
            }

            override fun showAttachment(data: AttachmentItem) {
                //File(URI(uri.toString()))
                println("### Testing show attachment : ${data.name}")
            }
        })*/

        viewModelHistoryStatus.getApprovalHistoryStatus().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
                Timber.i("### ambil dari getApprovalHistoryStatus $it")
            }
        })
    }

}