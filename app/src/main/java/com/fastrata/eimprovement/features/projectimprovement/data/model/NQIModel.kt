package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NQIModel(
    val benefit: Benefit? = null,
    val cost: Cost? = null,
    val nqi: NQI? = null
):Parcelable

@Parcelize
data class Benefit (
    val nilai_estimasi: Int,
    val keterangan_estimasi : String,
    val nilai_aktual : Int,
    val keterangan_aktual : String
    ):Parcelable

@Parcelize
data class Cost (
    val nilai_estimasi: Int,
    val keterangan_estimasi : String,
    val nilai_aktual : Int,
    val keterangan_aktual : String
):Parcelable

@Parcelize
data class NQI (
    val nilai_estimasi: Int,
    val nilai_aktual : Int,
):Parcelable

