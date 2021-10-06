package com.fastrata.eimprovement.features.approval.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApprovalHistoryStatusModel(
    val pic: String,
    val status: String,
    val comment: String,
    val date: String
): Parcelable

