package com.fastrata.eimprovement.features.changespoint.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChangePointCreateModel(
    @SerializedName("saldo")
    var saldo : Int?,
    @SerializedName("no_penukaran")
    var cpNo : String?,
    @SerializedName("nama_pembuat")
    var name: String?,
    @SerializedName("nik")
    var nik : String?,
    @SerializedName("cabang")
    var branch : String?,
    @SerializedName("department")
    var department : String?,
    @SerializedName("jabatan")
    var job : String?,
    @SerializedName("tgl_penukaran")
    var date : String?,
    @SerializedName("keterangan")
    var description: String?,
    @SerializedName("penukaran_hadiah")
    var reward : ArrayList<ChangePointRewardItem?>?,
    @SerializedName("riwayat")
    var history : ArrayList<RiwayatItem?>?

): Parcelable