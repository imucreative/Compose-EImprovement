package com.fastrata.eimprovement.features.projectimprovement.data

import com.fastrata.eimprovement.data.resultMutableLiveDataRemote
import javax.inject.Inject

class PiRemoteRepository @Inject constructor(private val remoteDataSource: PiRemoteDataSource){
    fun observeListPi(userId: Int) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestListPi(userId) }
    )

    fun observeDetailPi(id: Int, userId: Int) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestDetailPi(id, userId) }
    )
}