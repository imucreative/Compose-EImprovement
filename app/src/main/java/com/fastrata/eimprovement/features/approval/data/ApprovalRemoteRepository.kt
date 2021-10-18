package com.fastrata.eimprovement.features.approval.data

import com.fastrata.eimprovement.data.resultMutableLiveDataRemote
import javax.inject.Inject

class ApprovalRemoteRepository @Inject constructor(private val remoteDataSource: ApprovalRemoteDataSource){
    fun observeListApproval() = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListApproval() }
    )
}