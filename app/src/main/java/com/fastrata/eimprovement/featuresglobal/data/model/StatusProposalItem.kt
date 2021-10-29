package com.fastrata.eimprovement.featuresglobal.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatusProposalItem(
    @SerializedName("STATUS_PROPOSAL_ID")
    var id: Int,
    @SerializedName("STATUS_PROPOSAL_NAME")
    var status: String,
): Parcelable