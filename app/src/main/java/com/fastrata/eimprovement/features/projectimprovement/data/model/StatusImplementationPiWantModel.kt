package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatusImplementationPiWantModel(
    @SerializedName("STARTDATE_IDENTIFIKASI")
    val startIdentifikasiMasalah : String? = null,
    @SerializedName("ENDDATE_IDENTIFIKASI")
    val endIdentifikasiMasalah : String? = null,

    @SerializedName("STARTDATE_ANALISA")
    val startAnalisaData : String? = null,
    @SerializedName("ENDDATE_ANALISA")
    val endAnalisaData : String? = null,

    @SerializedName("STARTDATE_AKAR")
    val startAnalisaAkarMasalah : String? = null,
    @SerializedName("ENDDATE_AKAR")
    val endAnalisaAkarMasalah : String? = null,

    @SerializedName("STARTDATE_RENCANA")
    val startMenyusunRencana : String? = null,
    @SerializedName("ENDDATE_RENCANA")
    val endMenyusunRencana : String? = null,

    @SerializedName("STARTDATE_EVALUASI")
    val startImplementasiRencana : String? = null,
    @SerializedName("ENDDATE_EVALUASI")
    val endImplementasiRencana : String? = null,

    @SerializedName("STARTDATE_PERBAIKAN")
    val startAnalisPeriksaEvaluasi : String? = null,
    @SerializedName("ENDDATE_PERBAIKAN")
    val endAnalisPeriksaEvaluasi : String? = null
): Parcelable