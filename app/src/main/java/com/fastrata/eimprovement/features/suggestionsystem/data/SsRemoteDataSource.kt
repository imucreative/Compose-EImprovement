package com.fastrata.eimprovement.features.suggestionsystem.data

import com.fastrata.eimprovement.api.AppService
import com.fastrata.eimprovement.api.BaseDataSource
import javax.inject.Inject

class SsRemoteDataSource @Inject constructor(private val service: AppService) : BaseDataSource() {
    suspend fun requestListSs(
        userId: Int,
        limit: Int,
        page: Int,
        roleName: String
    ) = getResult {
        service.listSs(userId, limit, page, roleName)
    }

    suspend fun requestDetailSs(id: Int, userId: Int) = getResult {
        service.detailSs(id, userId)
    }
}