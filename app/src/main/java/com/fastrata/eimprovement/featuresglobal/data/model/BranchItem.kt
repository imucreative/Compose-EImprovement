package com.fastrata.eimprovement.featuresglobal.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BranchItem (
    @SerializedName("ORG_ID")
    var orgId: Int,
    @SerializedName("BRANCH")
    var branch: String,
): Parcelable