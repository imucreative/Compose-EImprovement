package com.fastrata.eimprovement.featuresglobal.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseUserItem (
    @SerializedName("USER_ID")
    var userId: Int,
    @SerializedName("NIK")
    var nik: String,
    @SerializedName("FULL_NAME")
    var fullName: String,
): Parcelable