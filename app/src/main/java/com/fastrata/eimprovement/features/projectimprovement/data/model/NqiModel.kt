package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NqiModel(
    @SerializedName("ESTIMASI")
    val estimasiModel: NqiEstimasiModel? = null,
    @SerializedName("AKTUAL")
    val aktualModel: NqiAktualModel? = null
):Parcelable




