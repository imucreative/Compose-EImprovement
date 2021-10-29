package com.fastrata.eimprovement.features.approval.data.model

import android.os.Parcelable
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposalItem
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApprovalModel(
    @SerializedName("PROPOSAL_H_ID")
    val id: Int,
    @SerializedName("PROPOSAL_NO")
    val typeNo: String,
    @SerializedName("TITLE")
    val title: String,
    @SerializedName("STATUS_PROPOSAL")
    val status: StatusProposalItem,
    @SerializedName("TYPE")
    val type: String,
    @SerializedName("NAME")
    val name: String,
    @SerializedName("BRANCH")
    val branch: String,
    @SerializedName("SUBBRANCH")
    val subBranch: String,
    @SerializedName("CREATED_DATE")
    val date: String
): Parcelable
