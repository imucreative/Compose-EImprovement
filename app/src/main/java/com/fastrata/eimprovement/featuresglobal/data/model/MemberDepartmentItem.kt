package com.fastrata.eimprovement.featuresglobal.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MemberDepartmentItem(
    @SerializedName("DEPARTMENT_ID")
    var id: Int,
    @SerializedName("DEPARTMENT_NAME")
    var department: String
) : Parcelable
