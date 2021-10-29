package com.fastrata.eimprovement.featuresglobal.data

import com.fastrata.eimprovement.data.resultMutableLiveDataRemote
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
}