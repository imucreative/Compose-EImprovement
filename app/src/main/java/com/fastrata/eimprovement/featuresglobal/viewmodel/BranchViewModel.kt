package com.fastrata.eimprovement.featuresglobal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fastrata.eimprovement.api.ResultsResponse
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.featuresglobal.data.GlobalRemoteRepository
import com.fastrata.eimprovement.featuresglobal.data.model.BranchItem
import com.fastrata.eimprovement.featuresglobal.data.model.SubBranchItem
import com.fastrata.eimprovement.wrapper.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BranchViewModel @Inject constructor(private val repository: GlobalRemoteRepository): ViewModel() {
    // === Branch
    private val _listBranch = MutableLiveData<Event<LiveData<Result<ResultsResponse<BranchItem>>>>>()
    val getBranchItem: LiveData<Event<LiveData<Result<ResultsResponse<BranchItem>>>>> get() = _listBranch

    fun setBranch() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.Default) { repository.observeListBranch() }
            _listBranch.value = Event(result)
        }
    }

    // === SubBranch
    private val _listSubBranch = MutableLiveData<Event<LiveData<Result<ResultsResponse<SubBranchItem>>>>>()
    val getSubBranchItem: LiveData<Event<LiveData<Result<ResultsResponse<SubBranchItem>>>>> get() = _listSubBranch

    fun setSubBranch(orgId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.Default) { repository.observeListSubBranch(orgId) }
            _listSubBranch.value = Event(result)
        }
    }
}