package com.fastrata.eimprovement.features.changespoint.data

import com.fastrata.eimprovement.data.resultMutableLiveDataRemote
import javax.inject.Inject

class CpRemoteRepository @Inject constructor(private val remoteDataSource: CpRemoteDataSource){
    fun observeListGift() = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListGift() }
    )

    fun observerListCp(
        userId: Int,
        limit: Int,
        page: Int,
        roleName: String
    ) = resultMutableLiveDataRemote(
        networkCall = {remoteDataSource.requestListCp(userId, limit, page, roleName) }
    )

    fun observerDetailCp(id: Int, userId: Int) = resultMutableLiveDataRemote(
        networkCall = {remoteDataSource.requestDetailCp(id, userId) }
    )
}