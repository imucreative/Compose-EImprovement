package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NqiModel(
    val estimasi: Estimasi? = null,
    val aktual: Aktual? = null
):Parcelable

@Parcelize
data class Estimasi (
    val benefit: Int? = null,
    val benefit_keterangan: String? = null,
    val cost: Int? = null,
    val cost_keterangan: String? = null,
    val nqi: Int? = null
):Parcelable

@Parcelize
data class Aktual (
    val benefit: Int? = null,
    val benefit_keterangan: String? = null,
    val cost: Int? = null,
    val cost_keterangan: String? = null,
    val nqi: Int? = null
):Parcelable
