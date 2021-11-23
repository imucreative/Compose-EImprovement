package com.fastrata.eimprovement.features.approval.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fastrata.eimprovement.api.ResultsResponse
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.features.approval.data.ApprovalRemoteRepository
import com.fastrata.eimprovement.features.approval.data.model.ApprovalHistoryStatusModel
import com.fastrata.eimprovement.features.approval.data.model.ApprovalModel
import com.fastrata.eimprovement.features.approval.data.model.ApprovalRemoteRequest
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.wrapper.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListApprovalViewModel @Inject constructor(private val repository: ApprovalRemoteRepository): ViewModel() {
    // History Approval
    private val listApprovalHistoryStatus = MutableLiveData<ArrayList<ApprovalHistoryStatusModel?>?>()
    fun setApprovalHistoryStatus(source: String, type: String) {
        var data: ArrayList<ApprovalHistoryStatusModel?>? = null
        if (type == SS) {
            data = HawkUtils().getTempDataCreateSs(source)?.historyApproval
        } else if (type == PI) {
            data = HawkUtils().getTempDataCreatePi(source)?.historyApproval
        }
        listApprovalHistoryStatus.postValue(data)
    }
    fun getApprovalHistoryStatus(): LiveData<ArrayList<ApprovalHistoryStatusModel?>?> {
        return listApprovalHistoryStatus
    }

    // === List Approval
    private val _listApproval = MutableLiveData<Event<LiveData<Result<ResultsResponse<ApprovalModel>>>>>()
    val getListApprovalItem: LiveData<Event<LiveData<Result<ResultsResponse<ApprovalModel>>>>> get() = _listApproval

    fun setListApproval(listApprovalRemoteRequest: ApprovalRemoteRequest) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.Default) { repository.observeListApproval(listApprovalRemoteRequest) }
            _listApproval.value = Event(result)
        }
    }
}