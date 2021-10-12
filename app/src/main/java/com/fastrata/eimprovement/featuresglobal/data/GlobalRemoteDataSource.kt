package com.fastrata.eimprovement.featuresglobal.data

import com.fastrata.eimprovement.api.AppService
import com.fastrata.eimprovement.api.BaseDataSource
import javax.inject.Inject

class GlobalRemoteDataSource @Inject constructor(private val service: AppService) : BaseDataSource(){
    suspend fun requestListCategory() = getResult {
        service.listCategory()
    }

    suspend fun requestListTeamMember(branchCode: String) = getResult {
        service.listTeamMember(branchCode)
    }

    suspend fun requestListDepartment() = getResult {
        service.listDepartment()
    }

    suspend fun requestListTeamRole() = getResult {
        service.listTeamRole()
    }

    suspend fun requestListStatusProposal() = getResult {
        service.listStatusProposal()
    }

    suspend fun requestListBranch() = getResult {
        service.listBranch()
    }

    suspend fun requestListSubBranch(orgId: Int) = getResult {
        service.listSubBranch(orgId)
    }
}