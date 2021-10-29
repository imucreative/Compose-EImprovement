package com.fastrata.eimprovement.features.changespoint.data

import com.fastrata.eimprovement.data.resultMutableLiveDataRemote
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointRemoteRequest
import javax.inject.Inject

class CpRemoteRepository @Inject constructor(private val remoteDataSource: CpRemoteDataSource){
    fun observeListGift() = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListGift() }
    )

    fun observerListCp(listChangePointRemoteRequest: ChangePointRemoteRequest) = resultMutableLiveDataRemote(
        networkCall = {remoteDataSource.requestListCp(listChangePointRemoteRequest) }
    )

    fun observerDetailCp(id: Int, userId: Int) = resultMutableLiveDataRemote(
        networkCall = {remoteDataSource.requestDetailCp(id, userId) }
    )
}