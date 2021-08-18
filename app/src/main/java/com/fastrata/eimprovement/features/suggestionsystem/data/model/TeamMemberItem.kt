package com.fastrata.eimprovement.features.suggestionsystem.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamMemberItem(
    var name: String,
    var department: String,
    var task: String
) : Parcelable