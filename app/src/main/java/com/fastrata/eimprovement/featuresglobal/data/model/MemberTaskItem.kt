package com.fastrata.eimprovement.featuresglobal.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MemberTaskItem(
    @SerializedName("TEAM_ROLE_ID")
    var id: Int,
    @SerializedName("TEAM_ROLE_NAME")
    var task: String
) : Parcelable
