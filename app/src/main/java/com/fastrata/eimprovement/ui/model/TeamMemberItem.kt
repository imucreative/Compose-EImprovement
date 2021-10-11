package com.fastrata.eimprovement.ui.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamMemberItem(
    @SerializedName("TEAM_MEMBER")
    var name: MemberNameItem?,
    @SerializedName("DEPARTMENT")
    var department: MemberDepartmentItem?,
    @SerializedName("TEAM_ROLE")
    var task: MemberTaskItem?
) : Parcelable

@Parcelize
data class MemberNameItem(
    @SerializedName("USER_ID")
    var id: Int,
    @SerializedName("FULL_NAME")
    var name: String
) : Parcelable

@Parcelize
data class MemberDepartmentItem(
    @SerializedName("DEPARTMENT_ID")
    var id: Int,
    @SerializedName("DEPARTMENT_NAME")
    var department: String
) : Parcelable

@Parcelize
data class MemberTaskItem(
    @SerializedName("TEAM_ROLE_ID")
    var id: Int,
    @SerializedName("TEAM_ROLE_NAME")
    var task: String
) : Parcelable
