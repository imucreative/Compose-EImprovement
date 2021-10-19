package com.fastrata.eimprovement.features.changespoint.data

import com.fastrata.eimprovement.api.AppService
import com.fastrata.eimprovement.api.BaseDataSource
import javax.inject.Inject

class CpRemoteDataSource @Inject constructor(private val service: AppService) : BaseDataSource(){
    suspend fun requestListGift() = getResult {
        service.listGift()
    }

    suspend fun requestListCp(userId: Int) = getResult {
        service.listCp(userId)
    }

    suspend fun requestDetailCp(id: Int, userId: Int) = getResult {
        service.detailCp(id, userId)
    }
}