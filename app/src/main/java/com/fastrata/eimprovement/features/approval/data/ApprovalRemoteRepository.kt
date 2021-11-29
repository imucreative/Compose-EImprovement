package com.fastrata.eimprovement.features.approval.data

import com.fastrata.eimprovement.data.resultMutableLiveDataRemote
import com.fastrata.eimprovement.features.approval.data.model.ApprovalRemoteRequest
import javax.inject.Inject

class ApprovalRemoteRepository @Inject constructor(private val remoteDataSource: ApprovalRemoteDataSource){
    fun observeListApproval(listApprovalRemoteRequest: ApprovalRemoteRequest) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListApproval(listApprovalRemoteRequest) }
    )
}