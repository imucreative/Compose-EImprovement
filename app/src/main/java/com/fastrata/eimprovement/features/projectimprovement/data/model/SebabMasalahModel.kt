package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SebabMasalahModel(
    @SerializedName("CAUSE_PROBLEM")
    val penyebab: String,
    @SerializedName("W1")
    val w1: String,
    @SerializedName("W2")
    val w2: String,
    @SerializedName("W3")
    val w3: String,
    @SerializedName("W4")
    val w4: String,
    @SerializedName("W5")
    val w5: String,
    @SerializedName("PRIORITY_PROBLEM")
    val prioritas : String,
): Parcelable