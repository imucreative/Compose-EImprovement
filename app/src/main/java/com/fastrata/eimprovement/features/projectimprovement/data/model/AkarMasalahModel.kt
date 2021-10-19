package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AkarMasalahModel(
    @SerializedName("SEQUENCE")
    val sequence : Int,
    @SerializedName("AKAR_MASALAH")
    val kenapa:String,
    @SerializedName("IMPROVEMENT")
    val aksi: String,
    @SerializedName("IMPROVEMENT_DETAIL")
    val detail_langkah : String
): Parcelable