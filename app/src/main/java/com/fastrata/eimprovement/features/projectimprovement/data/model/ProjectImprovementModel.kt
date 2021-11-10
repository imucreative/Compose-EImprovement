package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposalItem
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProjectImprovementModel(
    @SerializedName("PI_H_ID")
    val idPi: Int,
    @SerializedName("PI_NO")
    val piNo: String,
    @SerializedName("STATUS_PROPOSAL")
    val status: StatusProposalItem,
    @SerializedName("TITLE")
    val title: String,
    @SerializedName("CATEGORY")
    val category: String,
    @SerializedName("BRANCH")
    val branch: String,
    @SerializedName("SUBBRANCH")
    val subBranch: String,
    @SerializedName("CREATED_DATE")
    val date : String,
    @SerializedName("CREATED_BY")
    val createdBy : String,
    @SerializedName("IS_EDIT")
    val isEdit: Boolean,
    @SerializedName("IS_DELETE")
    val isDelete: Boolean,
    @SerializedName("IS_VIEW")
    val isView: Boolean
): Parcelable