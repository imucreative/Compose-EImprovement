package com.fastrata.eimprovement.features.changespoint.data

import com.fastrata.eimprovement.data.resultMutableLiveDataRemote
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointCreateModel
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointRemoteRequest
import javax.inject.Inject

class CpRemoteRepository @Inject constructor(private val remoteDataSource: CpRemoteDataSource){
    fun observeListGift() = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListGift() }
    )

    fun observerListCp(listChangePointRemoteRequest: ChangePointRemoteRequest) = resultMutableLiveDataRemote(
        networkCall = {remoteDataSource.requestListCp(listChangePointRemoteRequest) }
    )

    fun observerDetailCp(id: Int, userId: Int) = resultMutableLiveDataRemote(
        networkCall = {remoteDataSource.requestDetailCp(id, userId) }
    )

    fun observeSubmitCreateCp(changePointCreateModel: ChangePointCreateModel) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.postSubmitCreateCp(changePointCreateModel)}
    )

    fun observeSubmitUpdateCp(changePointCreateModel: ChangePointCreateModel) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.postSubmitUpdateCp(changePointCreateModel)}
    )

    fun observeRemoveCp(idCp: Int) = resultMutableLiveDataRemote (
        networkCall = { remoteDataSource.removeCp(idCp)}
    )
}