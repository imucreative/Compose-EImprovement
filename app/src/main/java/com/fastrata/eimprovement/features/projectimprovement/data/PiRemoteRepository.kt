package com.fastrata.eimprovement.features.projectimprovement.data

import com.fastrata.eimprovement.data.resultMutableLiveDataRemote
import javax.inject.Inject

class PiRemoteRepository @Inject constructor(private val remoteDataSource: PiRemoteDataSource){
    fun observeListCategory() = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListCategory() }
    )
}