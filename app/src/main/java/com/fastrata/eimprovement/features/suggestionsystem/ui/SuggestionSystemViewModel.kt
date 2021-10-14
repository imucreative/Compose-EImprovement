package com.fastrata.eimprovement.features.suggestionsystem.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fastrata.eimprovement.api.ResultsResponse
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.features.suggestionsystem.data.SsRemoteRepository
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemModel
import com.fastrata.eimprovement.utils.DataDummySs
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.wrapper.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SuggestionSystemViewModel @Inject constructor(private val repository: SsRemoteRepository): ViewModel() {
    private val detailSuggestionSystem = MutableLiveData<SuggestionSystemCreateModel>()

    fun setSuggestionSystemDetail(ssNo: String) {
        // koneksi ke DB
        println("# $ssNo")
        val data = DataDummySs.generateDummyDetailSuggestionSystem()
        println("##### setSuggestionSystemDetail in viewModel : $detailSuggestionSystem")
        detailSuggestionSystem.postValue(data)
    }

    fun getSuggestionSystemDetail(): LiveData<SuggestionSystemCreateModel> {
        println("##### getSuggestionSystemDetail $detailSuggestionSystem")
        return detailSuggestionSystem
    }

    // === List SS
    private val _listSs = MutableLiveData<Event<LiveData<Result<ResultsResponse<SuggestionSystemModel>>>>>()
    val getListSsItem: LiveData<Event<LiveData<Result<ResultsResponse<SuggestionSystemModel>>>>> get() = _listSs

    fun setListSs(userId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.Default) { repository.observeListSs(userId) }
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
}
