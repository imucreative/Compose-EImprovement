package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProjectImprovementItem(
    val piNo: String,
    val title: String,
    val status: String,
    val categoryRepairment: String,
    val branch: String,
    val subBranch: String,
    val date : String,
    val createby : String
): Parcelable