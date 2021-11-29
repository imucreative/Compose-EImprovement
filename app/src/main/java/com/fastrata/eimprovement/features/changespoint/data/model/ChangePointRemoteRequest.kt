package com.fastrata.eimprovement.features.changespoint.data.model

import com.google.gson.annotations.SerializedName

data class ChangePointRemoteRequest(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("roleName")
    val roleName: String,
    @SerializedName("typeProposal")
    val typeProposal: String,

    @SerializedName("userName")
    val userName: String,
    @SerializedName("rpNo")
    val cpNo: String,
    @SerializedName("statusId")
    val statusId: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("createdBy")
    val createdBy: String,
    @SerializedName("orgId")
    val orgId: Int,
    @SerializedName("warehouseId")
    val warehouseId: Int,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("endDate")
    val endDate: String
)
