package com.fastrata.eimprovement.features.dashboard.ui.data

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

class BalanceCreateViewModel @Inject constructor(private val repo :BalanceRemoteRepository) : ViewModel() {

    var userId: Int = 0

    private val _balance = MutableLiveData<Event<LiveData<Result<ResultsResponse<BalanceModel>>>>>()
    val getBalance : LiveData<Event<LiveData<Result<ResultsResponse<BalanceModel>>>>> get() = _balance
    fun setBalance(userId: Int){
        viewModelScope.launch(Dispatchers.Main){
            val result = withContext(Dispatchers.Default){ repo.observeBalance(userId) }
            _balance.value = Event(result)
        }
    }

    private val _calendar = MutableLiveData<Event<LiveData<Result<ResultsResponse<CalendarDashboardModel>>>>>()
    val getCalendarDashboard: LiveData<Event<LiveData<Result<ResultsResponse<CalendarDashboardModel>>>>> get() = _calendar
    fun setCalendarDashboard(year: Int){
        viewModelScope.launch(Dispatchers.Main){
            val result = withContext(Dispatchers.Default){ repo.observeCalendarDashboard(year) }
            _calendar.value = Event(result)
        }
    }

    val observeGetBalance by lazy {
        repo.observeBalance(userId)
    }
}