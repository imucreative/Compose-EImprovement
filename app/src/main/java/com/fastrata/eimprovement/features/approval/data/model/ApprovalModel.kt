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
    @SerializedName("USER_ID")
    val userId: Int,
    @SerializedName("NAME")
    val name: String,
    @SerializedName("BRANCH")
    val branch: String,
    @SerializedName("SUBBRANCH")
    val subBranch: String,
    @SerializedName("CREATED_DATE")
    val date: String,
    @SerializedName("IS_VIEW")
    val isView: Boolean,
    @SerializedName("IS_EDIT")
    val isEdit: Boolean,
    @SerializedName("IS_DELETE")
    val isDelete: Boolean,
    @SerializedName("IS_IMPLEMENTATION")
    val isImplementation: Boolean,
    @SerializedName("IS_SUBMIT")
    val isSubmit : Boolean,
    @SerializedName("IS_CHECK")
    val isCheck : Boolean,
    @SerializedName("IS_CHECK_FINAL")
    val isCheckFinal : Boolean,
    @SerializedName("IS_SUBMIT_LAPORAN")
    val isSubmitlaporan : Boolean,
    @SerializedName("IS_REVIEW")
    val isReview : Boolean,
    @SerializedName("IS_REVIEW_FINAL")
    val isReviewFinal : Boolean
): Parcelable
