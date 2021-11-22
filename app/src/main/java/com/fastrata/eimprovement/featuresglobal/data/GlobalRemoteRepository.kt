package com.fastrata.eimprovement.featuresglobal.data

import com.fastrata.eimprovement.data.resultMutableLiveDataRemote
import okhttp3.MultipartBody
import javax.inject.Inject

class GlobalRemoteRepository @Inject constructor(private val remoteDataSource: GlobalRemoteDataSource){
    fun observeListCategory() = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListCategory() }
    )

    fun observeListTeamMember(
        departmentId: Int,
        orgId: Int,
        warehouseId: Int,
    ) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListTeamMember(departmentId, orgId, warehouseId) }
    )

    fun observeListDepartment(
        departmentId: Int,
        orgId: Int,
        warehouseId: Int,
        proposalType: String
    ) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListDepartment(departmentId, orgId, warehouseId, proposalType) }
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

    fun observeRemoveSs(idSs: Int) = resultMutableLiveDataRemote (
        networkCall = { remoteDataSource.removeSs(idSs)})

    fun observeRemovePi(idPi: Int) = resultMutableLiveDataRemote (
        networkCall = { remoteDataSource.removePi(idPi)})
}