package com.fastrata.eimprovement.features.suggestionsystem.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatusImplementation(
    @SerializedName("IS_IMP")
    var status: Int,
    @SerializedName("IMP_START")
    var from: String,
    @SerializedName("IMP_END")
    var to: String
) : Parcelable