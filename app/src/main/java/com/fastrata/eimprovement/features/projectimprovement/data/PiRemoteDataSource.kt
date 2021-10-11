package com.fastrata.eimprovement.features.projectimprovement.data

import com.fastrata.eimprovement.api.AppService
import com.fastrata.eimprovement.api.BaseDataSource
import javax.inject.Inject

class PiRemoteDataSource @Inject constructor(private val service: AppService) : BaseDataSource() {
    suspend fun requestListCategory() = getResult {
        service.listCategory()
    }
}