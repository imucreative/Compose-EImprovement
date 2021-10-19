package com.fastrata.eimprovement.features.projectimprovement.data

import com.fastrata.eimprovement.api.AppService
import com.fastrata.eimprovement.api.BaseDataSource
import javax.inject.Inject

class PiRemoteDataSource @Inject constructor(private val service: AppService) : BaseDataSource() {
    suspend fun requestListPi(userId: Int) = getResult {
        service.listPi(userId)
    }

    suspend fun requestDetailPi(id: Int, userId: Int) = getResult {
        service.detailPi(id, userId)
    }
}