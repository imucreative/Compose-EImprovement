package com.fastrata.eimprovement.features.suggestionsystem.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatusImplementation(
    @SerializedName("status")
    var status: String,
    @SerializedName("sampai")
    var to: String,
    @SerializedName("dari")
    var from: String
) : Parcelable