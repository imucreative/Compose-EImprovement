package com.fastrata.eimprovement.features.login.data.model

import com.google.gson.annotations.SerializedName

data class LoginRemoteRequest(
    @SerializedName("user_name")
    var USER_NAME: String,
    @SerializedName("user_password")
    var USER_PASSWORD: String
)