package com.fastrata.eimprovement.featuresglobal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fastrata.eimprovement.api.ResultsResponse
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.featuresglobal.data.GlobalRemoteRepository
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposalItem
import com.fastrata.eimprovement.wrapper.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

class StatusProposalViewModel @Inject constructor(private val repository: GlobalRemoteRepository): ViewModel() {
    // === Status proposal
    private val _listStatusProposal = MutableLiveData<Event<LiveData<Result<ResultsResponse<StatusProposalItem>>>>>()
    val getStatusProposalItem: LiveData<Event<LiveData<Result<ResultsResponse<StatusProposalItem>>>>> get() = _listStatusProposal

    fun setStatusProposal() {
        viewModelScope.launch {
            val result = repository.observeListStatusProposal()
            _listStatusProposal.value = Event(result)
        }
    }
}