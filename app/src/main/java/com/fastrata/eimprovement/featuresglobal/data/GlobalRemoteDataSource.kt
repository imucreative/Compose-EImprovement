package com.fastrata.eimprovement.featuresglobal.data

import com.fastrata.eimprovement.api.AppService
import com.fastrata.eimprovement.api.BaseDataSource
import javax.inject.Inject

class GlobalRemoteDataSource @Inject constructor(private val service: AppService) : BaseDataSource(){
    suspend fun requestListCategory() = getResult {
        service.listCategory()
    }

    suspend fun requestListTeamMember(
        departmentId: Int,
        orgId: Int,
        warehouseId: Int,
    ) = getResult {
        service.listTeamMember(departmentId, orgId, warehouseId)
    }

    suspend fun requestListDepartment(
        departmentId: Int,
        orgId: Int,
        warehouseId: Int,
        proposalType: String
    ) = getResult {
        service.listDepartment(departmentId, orgId, warehouseId, proposalType)
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