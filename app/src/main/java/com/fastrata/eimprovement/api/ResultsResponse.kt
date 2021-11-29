package com.fastrata.eimprovement.api

import com.google.gson.annotations.SerializedName

data class ResultsResponse<T>(
    @SerializedName("success")
    val success: String,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("totalPage")
    val totalPage: Int,
    @SerializedName("data")
    val data: List<T>
)