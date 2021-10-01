package com.fastrata.eimprovement.ui.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamMemberItem(
    @SerializedName("nama")
    var name: MemberNameItem?,
    @SerializedName("department")
    var department: MemberDepartmentItem?,
    @SerializedName("peran")
    var task: MemberTaskItem?
) : Parcelable

@Parcelize
data class MemberNameItem(
    @SerializedName("id")
    var id: Int,
    @SerializedName("full_name")
    var name: String
) : Parcelable

@Parcelize
data class MemberDepartmentItem(
    @SerializedName("id")
    var id: Int,
    @SerializedName("department_name")
    var department: String
) : Parcelable

@Parcelize
data class MemberTaskItem(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var task: String
) : Parcelable
