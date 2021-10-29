package com.fastrata.eimprovement.features.approval.data

import com.fastrata.eimprovement.api.AppService
import com.fastrata.eimprovement.api.BaseDataSource
import javax.inject.Inject

class ApprovalRemoteDataSource @Inject constructor(private val service: AppService) : BaseDataSource() {
    suspend fun requestListApproval() = getResult {
        service.listApproval()
    }
}