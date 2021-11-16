package com.fastrata.eimprovement.features.suggestionsystem.data

import com.fastrata.eimprovement.api.AppService
import com.fastrata.eimprovement.api.BaseDataSource
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemRemoteRequest
import javax.inject.Inject

class SsRemoteDataSource @Inject constructor(private val service: AppService) : BaseDataSource() {
    suspend fun requestListSs(listSuggestionSystemRemoteRequest: SuggestionSystemRemoteRequest) = getResult {
        service.listSs(listSuggestionSystemRemoteRequest)
    }

    suspend fun requestDetailSs(id: Int, userId: Int) = getResult {
        service.detailSs(id, userId)
    }
}