package com.fastrata.eimprovement.features.projectimprovement.data

import com.fastrata.eimprovement.data.resultMutableLiveDataRemote
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementRemoteRequest
import javax.inject.Inject

class PiRemoteRepository @Inject constructor(private val remoteDataSource: PiRemoteDataSource){
    fun observeListPi(listProjectImprovementRemoteRequest: ProjectImprovementRemoteRequest) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListPi(listProjectImprovementRemoteRequest) }
    )

    fun observeDetailPi(id: Int, userId: Int) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestDetailPi(id, userId) }
    )

    fun observeSubmitCreatePi(projectImprovementCreateModel: ProjectImprovementCreateModel) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.postSubmitCreatePi(projectImprovementCreateModel) }
    )

    fun observeSubmitUpdatePi(projectImprovementCreateModel: ProjectImprovementCreateModel) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.postSubmitUpdatePi(projectImprovementCreateModel) }
    )

    fun observeRemovePi(idPi: Int) = resultMutableLiveDataRemote (
        networkCall = { remoteDataSource.removePi(idPi)}
    )
}