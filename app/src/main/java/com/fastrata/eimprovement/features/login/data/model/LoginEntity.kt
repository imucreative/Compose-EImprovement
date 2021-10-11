package com.fastrata.eimprovement.features.login.data.model

import com.google.gson.annotations.SerializedName

data class LoginEntity(
    @SerializedName("USER_ID")
    val USER_ID: Int,
    @SerializedName("NIK")
    val NIK: String,
    @SerializedName("USER_NAME")
    val USER_NAME: String,
    @SerializedName("FULL_NAME")
    val FULL_NAME: String,
    @SerializedName("HEAD_NAME")
    val DIRECT_MANAGER: String?,
    @SerializedName("EMAIL")
    val EMAIL: String?,
    @SerializedName("BRANCH_CODE")
    val BRANCH_CODE: String?,
    @SerializedName("BRANCH")
    val BRANCH: String?,
    @SerializedName("SUBBRANCH")
    val SUB_BRANCH: String?,
    @SerializedName("DEPARTMENT_NAME")
    val DEPARTMENT: String?,
    @SerializedName("JOB_LEVEL")
    val POSITION: String?,
    @SerializedName("JOB_TITLE")
    val JOB_TITLE: String?,
    @SerializedName("TOKEN")
    val TOKEN: String?,
    @SerializedName("API_KEY")
    val API_KEY: String?,
    @SerializedName("ROLES")
    val ROLES: List<RoleEntity>?
)