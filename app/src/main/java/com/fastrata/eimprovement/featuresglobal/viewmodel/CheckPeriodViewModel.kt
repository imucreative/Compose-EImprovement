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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CheckPeriodViewModel @Inject constructor(private val repository: GlobalRemoteRepository): ViewModel() {
    // === Check Period for Status proposal
    private val _checkPeriod = MutableLiveData<Event<LiveData<Result<ResultsResponse<StatusProposalItem>>>>>()
    val getCheckPeriodItem: LiveData<Event<LiveData<Result<ResultsResponse<StatusProposalItem>>>>> get() = _checkPeriod

    fun setCheckPeriod(typeProposal: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.Default) { repository.observeCheckPeriod(typeProposal) }
            _checkPeriod.value = Event(result)
        }
    }
}