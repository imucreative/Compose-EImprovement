package com.fastrata.eimprovement.features.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fastrata.eimprovement.api.ResultsResponse
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.features.login.data.model.LoginEntity
import com.fastrata.eimprovement.features.login.data.LoginRemoteRepository
import com.fastrata.eimprovement.features.login.data.model.LoginRemoteRequest
import com.fastrata.eimprovement.wrapper.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: LoginRemoteRepository) : ViewModel() {

    /*val masterCustomers by lazy {
        repository.observeMasterCustomers(surveyorCode)
    }*/

    /**
     * Tricky way to handle SingleLiveEvent
     * **/
    // getter
    val doLiveLogin: LiveData<Event<LiveData<Result<ResultsResponse<LoginEntity>>>>> get() = _doLiveLogin
    // init
    private val _doLiveLogin = MutableLiveData<Event<LiveData<Result<ResultsResponse<LoginEntity>>>>>()
    // setter
    fun processLogin(username: String, password: String) {
        viewModelScope.launch {
            val resultLogin = repository.observeLoginMutable(LoginRemoteRequest(username, password))
            _doLiveLogin.value = Event(resultLogin)
        }
    }
}