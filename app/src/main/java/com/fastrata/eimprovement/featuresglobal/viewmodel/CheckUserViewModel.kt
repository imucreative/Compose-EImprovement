package com.fastrata.eimprovement.featuresglobal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fastrata.eimprovement.api.ResultsResponse
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.featuresglobal.data.GlobalRemoteRepository
import com.fastrata.eimprovement.featuresglobal.data.model.ResponseUserItem
import com.fastrata.eimprovement.wrapper.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CheckUserViewModel @Inject constructor(private val repository: GlobalRemoteRepository): ViewModel() {
    private val _checkUser = MutableLiveData<Event<LiveData<Result<ResultsResponse<ResponseUserItem>>>>>()
    val getCheckUserItem: LiveData<Event<LiveData<Result<ResultsResponse<ResponseUserItem>>>>> get() = _checkUser

    fun setCheckUser(userId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.Default) { repository.observeCheckUser(userId) }
            _checkUser.value = Event(result)
        }
    }
}