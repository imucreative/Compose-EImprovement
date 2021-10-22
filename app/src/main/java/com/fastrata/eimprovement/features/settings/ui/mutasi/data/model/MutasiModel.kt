package com.fastrata.eimprovement.features.settings.ui.mutasi.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MutasiModel(
    @SerializedName("tanggal")
    val date: String,
    @SerializedName("tipe")
    val tipe: String,
    @SerializedName("no_doc")
    val document_no: String,
    @SerializedName("status")
    val stat: String,
    @SerializedName("keterangan")
    val desc: String,
    @SerializedName("pembuat")
    val created_by : String,
    @SerializedName("nominal")
    val nominal: Int,
    @SerializedName("tipe_point")
    val tipe_point : String
):Parcelable