package com.fastrata.eimprovement.features.suggestionsystem.data.model

import com.google.gson.annotations.SerializedName

data class SuggestionSystemRemoteRequest(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("roleName")
    val roleName: String,

    @SerializedName("userName")
    val userName: String,
    @SerializedName("statusId")
    val statusId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("orgId")
    val orgId: Int,
    @SerializedName("warehouseId")
    val warehouseId: Int,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("endDate")
    val endDate: String
)