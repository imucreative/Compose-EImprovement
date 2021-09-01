package com.fastrata.eimprovement.features.login.data

import com.fastrata.eimprovement.api.AppService
import com.fastrata.eimprovement.api.BaseDataSource
import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(private val service: AppService) : BaseDataSource() {
    suspend fun requestLogin(loginRequest: LoginRemoteRequest) = getResult {
        service.login(loginRequest)
    }

    /*suspend fun fetchCustomerMasterData(surveyorCode: String) = getResult {
        service.getCustomerMaster(surveyorCode)
    }*/
}