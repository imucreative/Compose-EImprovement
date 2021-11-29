package com.fastrata.eimprovement.features.dashboard.ui.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fastrata.eimprovement.api.ResultsResponse
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointModel
import com.fastrata.eimprovement.wrapper.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BalanceCreateViewModel @Inject constructor(private val repo :BalanceRemoteRepository) : ViewModel() {

    private val _balance = MutableLiveData<Event<LiveData<Result<ResultsResponse<BalanceModel>>>>>()
    val getBalance : LiveData<Event<LiveData<Result<ResultsResponse<BalanceModel>>>>> get() = _balance

    fun setBalance(
        userId: Int
    ){
        viewModelScope.launch(Dispatchers.Main){
            val result = withContext(Dispatchers.Default){ repo.observeBalance(userId)
            }
            _balance.value = Event(result)
        }
    }
}