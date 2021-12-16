package com.fastrata.eimprovement.features.projectimprovement.data

import com.fastrata.eimprovement.api.AppService
import com.fastrata.eimprovement.api.BaseDataSource
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementRemoteRequest
import javax.inject.Inject

class PiRemoteDataSource @Inject constructor(private val service: AppService) : BaseDataSource() {
    suspend fun requestListPi(listProjectImprovementRemoteRequest: ProjectImprovementRemoteRequest) = getResult {
        service.listPi(listProjectImprovementRemoteRequest)
    }

    suspend fun requestDetailPi(id: Int, userId: Int) = getResult {
        service.detailPi(id, userId)
    }

    suspend fun postSubmitCreatePi(projectImprovementCreateModel: ProjectImprovementCreateModel, action: String) = getResult {
        service.submitCreatePi(projectImprovementCreateModel, action)
    }

    suspend fun postSubmitUpdatePi(projectImprovementCreateModel: ProjectImprovementCreateModel)  = getResult {
        service.submitUpdatePi(projectImprovementCreateModel)
    }

    suspend fun removePi(idPi : Int) = getResult {
        service.removePi(idPi)
    }
}