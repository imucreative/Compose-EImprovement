package com.fastrata.eimprovement.ui.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatusProposal(
    @SerializedName("id")
    var id: Int,
    @SerializedName("status")
    var status: String,
): Parcelable