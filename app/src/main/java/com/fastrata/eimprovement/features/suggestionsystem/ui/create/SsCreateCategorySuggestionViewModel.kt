package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fastrata.eimprovement.api.ResultsResponse
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.features.suggestionsystem.data.SsRemoteRepository
import com.fastrata.eimprovement.ui.model.CategoryImprovementItem
import com.fastrata.eimprovement.wrapper.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

class SsCreateCategorySuggestionViewModel @Inject constructor(private val repository: SsRemoteRepository): ViewModel() {
    private val _listCategory = MutableLiveData<Event<LiveData<Result<ResultsResponse<CategoryImprovementItem>>>>>()
    val getCategorySuggestion: LiveData<Event<LiveData<Result<ResultsResponse<CategoryImprovementItem>>>>> get() = _listCategory

    fun setCategorySuggestion() {
        viewModelScope.launch {
            val result = repository.observeListCategory()
            _listCategory.value = Event(result)
        }
    }
}