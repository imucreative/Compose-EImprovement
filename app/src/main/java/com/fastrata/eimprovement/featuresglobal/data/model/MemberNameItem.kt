package com.fastrata.eimprovement.featuresglobal.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MemberNameItem(
    @SerializedName("USER_ID")
    var id: Int,
    @SerializedName("FULL_NAME")
    var name: String
) : Parcelable
