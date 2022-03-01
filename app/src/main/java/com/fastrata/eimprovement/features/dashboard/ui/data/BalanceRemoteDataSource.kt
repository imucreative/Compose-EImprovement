package com.fastrata.eimprovement.features.dashboard.ui.data

import com.fastrata.eimprovement.api.AppService
import com.fastrata.eimprovement.api.BaseDataSource
import javax.inject.Inject

class BalanceRemoteDataSource @Inject constructor(private val service:AppService) :BaseDataSource() {

    suspend fun requestBalance(userId:Int) = getResult {
        service.getBalance(userId)
    }

    suspend fun requestCalendarDashboard(year: Int, month: Int) = getResult {
        service.getCalendarDashboard(year, month)
    }
}