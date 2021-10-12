package com.fastrata.eimprovement.featuresglobal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fastrata.eimprovement.api.ResultsResponse
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.featuresglobal.data.GlobalRemoteRepository
import com.fastrata.eimprovement.featuresglobal.data.model.CategoryImprovementItem
import com.fastrata.eimprovement.wrapper.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryViewModel @Inject constructor(private val repository: GlobalRemoteRepository): ViewModel(){
    private val _listCategory = MutableLiveData<Event<LiveData<Result<ResultsResponse<CategoryImprovementItem>>>>>()
    val getCategory: LiveData<Event<LiveData<Result<ResultsResponse<CategoryImprovementItem>>>>> get() = _listCategory

    fun setCategory() {
        viewModelScope.launch {
            val result = repository.observeListCategory()
            _listCategory.value = Event(result)
        }
    }
}