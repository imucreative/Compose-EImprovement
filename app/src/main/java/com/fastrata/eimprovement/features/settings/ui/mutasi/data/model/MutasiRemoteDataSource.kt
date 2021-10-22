package com.fastrata.eimprovement.features.settings.ui.mutasi.data.model

import com.fastrata.eimprovement.api.AppService
import com.fastrata.eimprovement.api.BaseDataSource
import javax.inject.Inject

class MutasiRemoteDataSource @Inject constructor(private val service : AppService): BaseDataSource(){
    suspend fun requestListMutasi(
        userId:Int
    ) = getResult {
        service.getBalancedetail(userId)
    }
}