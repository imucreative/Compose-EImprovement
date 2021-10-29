package com.fastrata.eimprovement.featuresglobal.data.model

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
