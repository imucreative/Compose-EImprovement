package com.fastrata.eimprovement.featuresglobal.data

import com.fastrata.eimprovement.data.resultMutableLiveDataRemote
import javax.inject.Inject

class GlobalRemoteRepository @Inject constructor(private val remoteDataSource: GlobalRemoteDataSource){
    fun observeListCategory() = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListCategory() }
    )

    fun observeListTeamMember(branchCode: String) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListTeamMember(branchCode) }
    )

    fun observeListDepartment() = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListDepartment() }
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