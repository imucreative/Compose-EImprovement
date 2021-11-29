package com.fastrata.eimprovement.features.approval.data

import com.fastrata.eimprovement.api.AppService
import com.fastrata.eimprovement.api.BaseDataSource
import com.fastrata.eimprovement.features.approval.data.model.ApprovalRemoteRequest
import javax.inject.Inject

class ApprovalRemoteDataSource @Inject constructor(private val service: AppService) : BaseDataSource() {
    suspend fun requestListApproval(listApprovalRemoteRequest: ApprovalRemoteRequest) = getResult {
        service.listApproval(listApprovalRemoteRequest)
    }
}