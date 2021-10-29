package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NqiAktualModel (
    @SerializedName("AKTUAL_BENEFIT")
    val benefit: Int? = null,
    @SerializedName("AKTUAL_BENEFIT_DESC")
    val benefit_keterangan: String? = null,
    @SerializedName("AKTUAL_COST")
    val cost: Int? = null,
    @SerializedName("AKTUAL_COST_DESC")
    val cost_keterangan: String? = null,
    @SerializedName("AKTUAL_NQI")
    val nqi: Int? = null
): Parcelable