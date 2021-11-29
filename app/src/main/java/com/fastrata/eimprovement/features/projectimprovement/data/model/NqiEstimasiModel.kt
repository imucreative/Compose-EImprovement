package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NqiEstimasiModel (
    @SerializedName("ESTIMASI_BENEFIT")
    val benefit: Int? = null,
    @SerializedName("ESTIMASI_BENEFIT_DESC")
    val benefit_keterangan: String? = null,
    @SerializedName("ESTIMASI_COST")
    val cost: Int? = null,
    @SerializedName("ESTIMASI_COST_DESC")
    val cost_keterangan: String? = null,
    @SerializedName("ESTIMASI_NQI")
    val nqi: Int? = null
): Parcelable