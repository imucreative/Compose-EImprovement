package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SebabMasalahModel(
    @SerializedName("CAUSE_PROBLEM")
    val penyebab: String,
    @SerializedName("w1")
    val w1: String,
    @SerializedName("w2")
    val w2: String,
    @SerializedName("w3")
    val w3: String,
    @SerializedName("w4")
    val w4: String,
    @SerializedName("w5")
    val w5: String,
    @SerializedName("PRIORITY_PROBLEM")
    val prioritas : String,
): Parcelable