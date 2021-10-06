package com.fastrata.eimprovement.features.login.data.model

import com.google.gson.annotations.SerializedName

data class LoginRemoteRequest(
    /*@SerializedName("user_name")
    var userName: String,
    @SerializedName("user_password")
    var userPassword: String*/

    @SerializedName("surveyor_code")
    var USER_NAME: String,
    @SerializedName("surveyor_password")
    var USER_PASSWORD: String
)