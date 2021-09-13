package com.fastrata.eimprovement.features.suggestionsystem.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamMemberItem(
    @SerializedName("nama")
    var name: String?,
    @SerializedName("department")
    var department: String?,
    @SerializedName("peran")
    var task: String?
) : Parcelable

@Parcelize
data class MemberNameItem(
    var name: String
) : Parcelable

@Parcelize
data class MemberDepartmentItem(
    var department: String
) : Parcelable

@Parcelize
data class MemberTaskItem(
    var task: String
) : Parcelable