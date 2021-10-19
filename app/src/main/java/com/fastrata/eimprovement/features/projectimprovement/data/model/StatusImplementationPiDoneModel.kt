package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatusImplementationPiDoneModel(
    @SerializedName("IMP_START")
    val from : String,
    @SerializedName("IMP_END")
    val to: String
): Parcelable