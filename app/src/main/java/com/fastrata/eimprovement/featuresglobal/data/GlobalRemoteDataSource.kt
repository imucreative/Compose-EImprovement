package com.fastrata.eimprovement.featuresglobal.data

import com.fastrata.eimprovement.api.AppService
import com.fastrata.eimprovement.api.BaseDataSource
import okhttp3.MultipartBody
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

    suspend fun uploadAttachment(file: MultipartBody.Part, type: String, group: String, createdBy: String) = getResult {
        service.uploadAttachment(file, type, group, createdBy)
    }

    suspend fun removeAttachment(attachmentId: Int, fileName: String, type: String) = getResult {
        service.removeAttachment(attachmentId, fileName, type)
    }

    suspend fun requestCheckPeriod(typeProposal: String) = getResult {
        service.checkPeriod(typeProposal)
    }

    suspend fun removeSs(idSs : Int) = getResult {
        service.removeSs(idSs)
    }

    suspend fun removePi(idPi : Int) = getResult {
        service.removePI(idPi)
    }
}