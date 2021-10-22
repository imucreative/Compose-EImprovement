package com.fastrata.eimprovement.features.settings.ui.mutasi.data.model

import com.fastrata.eimprovement.data.resultMutableLiveDataRemote
import javax.inject.Inject

class MutasiRemoteRepository @Inject constructor(private val remoteDataSource : MutasiRemoteDataSource) {

    fun observeListMutasi(
        userId : Int,
        limit: Int,
        page: Int
    )= resultMutableLiveDataRemote (
        networkCall = { remoteDataSource.requestListMutasi(userId,limit,page)}
    )
}