package com.fastrata.eimprovement.featuresglobal.data

import com.fastrata.eimprovement.data.resultMutableLiveDataRemote
import okhttp3.MultipartBody
import javax.inject.Inject

class GlobalRemoteRepository @Inject constructor(private val remoteDataSource: GlobalRemoteDataSource){
    fun observeListCategory() = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListCategory() }
    )

    fun observeListTeamMember(
        departmentName: String,
        userId: Int,
        role: Int
    ) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListTeamMember(departmentName, userId, role) }
    )

    fun observeListDepartment(
        userId: Int,
        proposalType: String
    ) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListDepartment(userId, proposalType) }
    )

    fun observeListTeamRole() = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListTeamRole() }
    )

    fun observeListStatusProposal() = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListStatusProposal() }
    )

    fun observeListBranch() = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListBranch() }
    )

    fun observeListSubBranch(orgId: Int) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListSubBranch(orgId) }
    )

    fun observeUploadAttachment(file: MultipartBody.Part, type: String, group: String, createdBy: String) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.uploadAttachment(file, type, group, createdBy) }
    )

    fun observeRemoveAttachment(attachmentId: Int, fileName: String, type: String) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.removeAttachment(attachmentId, fileName, type) }
    )

    fun observeCheckPeriod(typeProposal: String) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestCheckPeriod(typeProposal) }
    )

    fun observeCheckUser(userId: Int) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestCheckUser(userId) }
    )
}