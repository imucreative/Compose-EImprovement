package com.fastrata.eimprovement.features.suggestionsystem.data.model

import com.google.gson.annotations.SerializedName

data class SsRemoteRequest(
    @SerializedName("no_ss")
    val SS_NO: String,
    @SerializedName("status")
    val STATUS: String,
    @SerializedName("judul")
    val TITLE: String,
    @SerializedName("kategori")
    val CATEGORY: String,
    @SerializedName("cabang")
    val BRANCH: String,
    @SerializedName("subcabang")
    val SUB_BRANCH: String,
    @SerializedName("tgl_dibuat")
    val CREATED_DATE: String,
    @SerializedName("dibuat_oleh")
    val CREATED_BY: String
)