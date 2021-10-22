package com.fastrata.eimprovement.features.settings.ui.changepassword.data.model

import com.fastrata.eimprovement.data.resultMutableLiveDataRemote
import javax.inject.Inject

class ChangePasswordRemoteRepository @Inject constructor(private val remoteDataSource : ChangePasswordRemoteDataSource) {

    fun observeChangePassword(
        userId : Int,
        userName : String,
        userPassword : String,
        newPassword : String
    ) = resultMutableLiveDataRemote (
        networkCall = {remoteDataSource.requestChangePassword(userId, userName, userPassword, newPassword)}
    )
}