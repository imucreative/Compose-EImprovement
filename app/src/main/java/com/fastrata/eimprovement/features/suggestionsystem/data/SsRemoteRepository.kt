package com.fastrata.eimprovement.features.suggestionsystem.data

import com.fastrata.eimprovement.data.resultMutableLiveDataRemote
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SsRemoteRepository @Inject constructor(private val remoteDataSource: SsRemoteDataSource){
    fun observeListSs(
        userId: Int,
        limit: Int,
        page: Int,
        roleName: String
    ) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListSs(userId, limit, page, roleName) }
    )

    fun observeDetailSs(id: Int, userId: Int) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestDetailSs(id, userId) }
    )
}