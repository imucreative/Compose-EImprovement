package com.fastrata.eimprovement.features.changespoint.data

import com.fastrata.eimprovement.api.AppService
import com.fastrata.eimprovement.api.BaseDataSource
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointCreateModel
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointRemoteRequest
import javax.inject.Inject

class CpRemoteDataSource @Inject constructor(private val service: AppService) : BaseDataSource(){
    suspend fun requestListGift() = getResult {
        service.listGift()
    }

    suspend fun requestListCp(listChangePointRemoteRequest: ChangePointRemoteRequest) = getResult {
        service.listCp(listChangePointRemoteRequest)
    }

    suspend fun requestDetailCp(id: Int, userId: Int) = getResult {
        service.detailCp(id, userId)
    }

    suspend fun postSubmitCreateCp(changePointCreateModel: ChangePointCreateModel)= getResult {
        service.submitCreateCp(changePointCreateModel)
    }

    suspend fun postSubmitUpdateCp(changePointCreateModel: ChangePointCreateModel)= getResult {
        service.submitUpdateCp(changePointCreateModel)
    }

    suspend fun removeCp(idCp: Int) = getResult {
        service.removeCp(idCp)
    }

}