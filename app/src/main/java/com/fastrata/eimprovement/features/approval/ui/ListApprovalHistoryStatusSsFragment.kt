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
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
import com.fastrata.eimprovement.utils.ACTION_DETAIL_DATA
import com.fastrata.eimprovement.utils.SS_CREATE
import com.fastrata.eimprovement.utils.SS_DETAIL_DATA
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

        //data = HawkUtils().getTempDataCreateSs(source)
        viewModelHistoryStatus.setApprovalHistoryStatus()

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

            /*getAttachment.setOnClickListener {
                openFolder()
            }*/
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