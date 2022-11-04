package com.fastrata.eimprovement.features.settings.ui.changepassword.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fastrata.eimprovement.api.ResultsResponse
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.wrapper.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChangePasswordCreateViewModel @Inject constructor(private val repo : ChangePasswordRemoteRepository): ViewModel() {
    private val _changePassword = MutableLiveData<Event<LiveData<Result<ResultsResponse<ChangePasswordModel>>>>>()
    val getChangePassword : LiveData<Event<LiveData<Result<ResultsResponse<ChangePasswordModel>>>>> get() = _changePassword

    fun setChangePassword(
        userId : Int,
        userName : String,
        userPassword : String,
        newPassword : String
    ){
        viewModelScope.launch (Dispatchers.Main){
            val result = withContext(Dispatchers.Default){ repo.observeChangePassword(userId, userName, userPassword, newPassword)
            }
            _changePassword.value = Event(result)
        }
    }
}