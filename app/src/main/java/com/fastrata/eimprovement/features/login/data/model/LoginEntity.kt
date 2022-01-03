package com.fastrata.eimprovement.features.login.data.model

import com.google.gson.annotations.SerializedName

data class LoginEntity(
    @SerializedName("USER_ID")
    val USER_ID: Int,
    @SerializedName("ROLE_ID")
    val ROLE_ID: Int,
    @SerializedName("ROLE_NAME")
    val ROLE_NAME: String,
    @SerializedName("NIK")
    val NIK: String?,
    @SerializedName("USER_NAME")
    val USER_NAME: String,
    @SerializedName("FULL_NAME")
    val FULL_NAME: String?,
    @SerializedName("JABATAN")
    val JABATAN: String?,
    @SerializedName("HEAD_ID")
    val DIRECT_MANAGER_ID: Int?,
    @SerializedName("HEAD_NAME")
    val DIRECT_MANAGER: String?,
    @SerializedName("HEAD_NIK")
    val DIRECT_MANAGER_NIK: String?,
    @SerializedName("JABATAN_ATASAN")
    val JABATAN_ATASAN: String?,
    @SerializedName("EMAIL")
    val EMAIL: String?,
    @SerializedName("ORG_ID")
    val ORG_ID: Int?,
    @SerializedName("WAREHOUSE_ID")
    val WAREHOUSE_ID: Int?,
    @SerializedName("LOKASI")
    val LOKASI: String?,
    @SerializedName("BRANCH_CODE")
    val BRANCH_CODE: String?,
    @SerializedName("BRANCH")
    val BRANCH: String?,
    @SerializedName("SUBBRANCH")
    val SUB_BRANCH: String?,
    //@SerializedName("DEPARTMENT_ID")
    //val DEPARTMENT_ID: Int?,
    @SerializedName("DEPARTMENT_NAME")
    val DEPARTMENT: String?,
    @SerializedName("JOB_LEVEL_ID")
    val POSITION_ID: Int?,
    @SerializedName("JOB_LEVEL")
    val POSITION: String?,
    /*@SerializedName("JOB_TITLE_ID")
    val JOB_TITLE_ID: Int?,
    @SerializedName("JOB_TITLE")
    val JOB_TITLE: String?,*/
    @SerializedName("TOKEN")
    val TOKEN: String?,
    @SerializedName("API_KEY")
    val API_KEY: String?,
    @SerializedName("ROLES")
    val ROLES: List<RoleEntity>?
)