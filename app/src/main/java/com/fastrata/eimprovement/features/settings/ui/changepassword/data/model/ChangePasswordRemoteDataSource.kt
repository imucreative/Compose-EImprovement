package com.fastrata.eimprovement.features.settings.ui.changepassword.data.model

import com.fastrata.eimprovement.api.AppService
import com.fastrata.eimprovement.api.BaseDataSource
import javax.inject.Inject

class ChangePasswordRemoteDataSource @Inject constructor(private val service: AppService): BaseDataSource() {

    suspend fun requestChangePassword(
        userId: Int,
        userName : String,
        userPassword: String,
        newPassword: String
    )= getResult {
        service.changePassword(userId,userName,userPassword,newPassword)
    }
}