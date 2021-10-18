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
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementModel
import com.fastrata.eimprovement.utils.DataDummySs
import com.fastrata.eimprovement.wrapper.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListApprovalViewModel @Inject constructor(private val repository: ApprovalRemoteRepository): ViewModel() {
    private val listApprovalHistoryStatus = MutableLiveData<ArrayList<ApprovalHistoryStatusModel>>()
    fun setApprovalHistoryStatus() {
        // koneksi ke DB
        val data = DataDummySs.generateDummyApprovalHistoryStatus()

        listApprovalHistoryStatus.postValue(data)
    }
    fun getApprovalHistoryStatus(): LiveData<ArrayList<ApprovalHistoryStatusModel>> {
        return listApprovalHistoryStatus
    }

    // === List Approval
    private val _listApproval = MutableLiveData<Event<LiveData<Result<ResultsResponse<ApprovalModel>>>>>()
    val getListApprovalItem: LiveData<Event<LiveData<Result<ResultsResponse<ApprovalModel>>>>> get() = _listApproval

    fun setListApproval() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.Default) { repository.observeListApproval() }
            _listApproval.value = Event(result)
        }
    }
}