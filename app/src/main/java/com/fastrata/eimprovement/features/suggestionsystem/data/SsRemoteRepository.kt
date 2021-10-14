package com.fastrata.eimprovement.features.suggestionsystem.data

import com.fastrata.eimprovement.data.resultMutableLiveDataRemote
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SsRemoteRepository @Inject constructor(private val remoteDataSource: SsRemoteDataSource){
    fun observeListSs(userId: Int) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListSs(userId) }
    )
}