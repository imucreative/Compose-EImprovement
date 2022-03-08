package com.fastrata.eimprovement.features.dashboard.ui.data

import com.fastrata.eimprovement.data.resultLiveDataRemote
import com.fastrata.eimprovement.data.resultMutableLiveDataRemote
import javax.inject.Inject

class BalanceRemoteRepository @Inject constructor(private val remoteDataSource: BalanceRemoteDataSource) {

    fun observeBalance(userId: Int) = resultLiveDataRemote (
        networkCall = {remoteDataSource.requestBalance(userId)}
    )

    fun observeCalendarDashboard(year: Int) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestCalendarDashboard(year) }
    )
}