package com.fastrata.eimprovement.features.login.data.model

import com.google.gson.annotations.SerializedName

data class LoginEntity(
    /*@SerializedName("user_id")
    val userId: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("token")
    val token: Int,
    @SerializedName("api_key")
    val apiKey: String,
    @SerializedName("roles")
    val roles: List<String>*/

    @SerializedName("SURVEYOR_ID")
    val USER_ID: Int,
    @SerializedName("SURVEYOR_NAME")
    val USER_NAME: String,
    @SerializedName("SURVEYOR_CODE")
    val TOKEN: String,
    @SerializedName("ORG_ID")
    val API_KEY: String,
    @SerializedName("WAREHOUSE_ID")
    val ROLES: String
)