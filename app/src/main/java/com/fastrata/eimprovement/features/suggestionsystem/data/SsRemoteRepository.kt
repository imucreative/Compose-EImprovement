package com.fastrata.eimprovement.features.suggestionsystem.data

import com.fastrata.eimprovement.data.resultMutableLiveDataRemote
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SsRemoteRepository @Inject constructor(private val remoteDataSource: SsRemoteDataSource){
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
}