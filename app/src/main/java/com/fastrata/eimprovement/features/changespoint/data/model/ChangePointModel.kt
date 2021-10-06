package com.fastrata.eimprovement.features.changespoint.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChangePointModel(
    @SerializedName("no_penukaran")
    val cpNo: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("tgl_input")
    val date: String,
    @SerializedName("deskripsi")
    val desc: String,
    @SerializedName("cabang")
    val branch: String,
    @SerializedName("subcabang")
    val subBranch: String,
    @SerializedName("total")
    val total: String
):Parcelable
