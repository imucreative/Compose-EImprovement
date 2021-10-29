package com.fastrata.eimprovement.features.suggestionsystem.data

import com.fastrata.eimprovement.data.resultMutableLiveDataRemote
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemRemoteRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SsRemoteRepository @Inject constructor(private val remoteDataSource: SsRemoteDataSource){
    fun observeListSs(listSuggestionSystemRemoteRequest: SuggestionSystemRemoteRequest) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListSs(listSuggestionSystemRemoteRequest) }
    )

    fun observeDetailSs(id: Int, userId: Int) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestDetailSs(id, userId) }
    )
}