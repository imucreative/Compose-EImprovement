package com.fastrata.eimprovement.features.suggestionsystem.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fastrata.eimprovement.api.ResultsResponse
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.features.suggestionsystem.data.SsRemoteRepository
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemRemoteRequest
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemResponseModel
import com.fastrata.eimprovement.featuresglobal.data.GlobalRemoteRepository
import com.fastrata.eimprovement.wrapper.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SuggestionSystemViewModel @Inject constructor(private val repository: SsRemoteRepository,private val globalrepository : GlobalRemoteRepository): ViewModel() {

    // === List SS
    private val _listSs = MutableLiveData<Event<LiveData<Result<ResultsResponse<SuggestionSystemModel>>>>>()
    val getListSsItem: LiveData<Event<LiveData<Result<ResultsResponse<SuggestionSystemModel>>>>> get() = _listSs

    fun setListSs(listSuggestionSystemRemoteRequest: SuggestionSystemRemoteRequest) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.Default) { repository.observeListSs(listSuggestionSystemRemoteRequest) }
            _listSs.value = Event(result)
        }
    }

    // === Detail SS
    private val _detailSs = MutableLiveData<Event<LiveData<Result<ResultsResponse<SuggestionSystemCreateModel>>>>>()
    val getDetailSsItem: LiveData<Event<LiveData<Result<ResultsResponse<SuggestionSystemCreateModel>>>>> get() = _detailSs

    fun setDetailSs(idSs: Int, userId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.Default) { repository.observeDetailSs(idSs, userId) }
            _detailSs.value = Event(result)
        }
    }

    // === Post Submit Create SS
    private val _postSubmitCreateSs = MutableLiveData<Event<LiveData<Result<ResultsResponse<SuggestionSystemResponseModel>>>>>()
    val postSubmitCreateSs: LiveData<Event<LiveData<Result<ResultsResponse<SuggestionSystemResponseModel>>>>> get() = _postSubmitCreateSs

    fun setPostSubmitCreateSs(suggestionSystemCreateModel: SuggestionSystemCreateModel) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.Default) { repository.observeSubmitCreateSs(suggestionSystemCreateModel) }
            _postSubmitCreateSs.value = Event(result)
        }
    }

    // === Put Submit Update SS
    private val _postSubmitUpdateSs = MutableLiveData<Event<LiveData<Result<ResultsResponse<SuggestionSystemResponseModel>>>>>()
    val putSubmitUpdateSs: LiveData<Event<LiveData<Result<ResultsResponse<SuggestionSystemResponseModel>>>>> get() = _postSubmitUpdateSs

    fun setPostSubmitUpdateSs(suggestionSystemCreateModel: SuggestionSystemCreateModel) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.Default) { repository.observeSubmitUpdateSs(suggestionSystemCreateModel) }
            _postSubmitUpdateSs.value = Event(result)
        }
    }

    private val _removeListSs = MutableLiveData<Event<LiveData<Result<ResultsResponse<ArrayList<String>>>>>>()
    val doRemoveSs : LiveData<Event<LiveData<Result<ResultsResponse<ArrayList<String>>>>>> get() = _removeListSs

    fun deleteSsList(idSs: Int){
        viewModelScope.launch(Dispatchers.Main){
            val result =  withContext(Dispatchers.Default) { globalrepository.observeRemoveSs(idSs)}
            _removeListSs.value = Event(result)
        }

    }
}
