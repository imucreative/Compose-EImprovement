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
        departmentName: String,
        userId: Int,
        role: Int
    ) = getResult {
        service.listTeamMember(departmentName, userId, role)
    }

    suspend fun requestListDepartment(
        userId: Int,
        proposalType: String
    ) = getResult {
        service.listDepartment(userId, proposalType)
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

    suspend fun requestCheckUser(userId: Int) = getResult {
        service.checkUser(userId)
    }
}