package com.fastrata.eimprovement.features.approval.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApprovalModel(
    val id: Int,
    val typeNo: String,
    val title: String,
    val status: String,
    val type: String,
    val name: String,
    val nik: String,
    val branch: String,
    val subBranch: String,
    val date: String
): Parcelable
