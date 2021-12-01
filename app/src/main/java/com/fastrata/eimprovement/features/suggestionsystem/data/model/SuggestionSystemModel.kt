package com.fastrata.eimprovement.features.suggestionsystem.data.model

import android.os.Parcelable
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposalItem
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SuggestionSystemModel(
    @SerializedName("SS_H_ID")
    val idSs: Int,
    @SerializedName("SS_NO")
    val ssNo: String,
    @SerializedName("USER_ID")
    val userId: Int,
    @SerializedName("CREATED_DATE")
    val date: String,
    @SerializedName("TITLE")
    val title: String,
    @SerializedName("STATUS_PROPOSAL")
    val status: StatusProposalItem,
    @SerializedName("CATEGORY")
    val categoryRepairment: String,
    @SerializedName("BRANCH")
    val branch: String,
    @SerializedName("SUBBRANCH")
    val subBranch: String,
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
    @SerializedName("IS_SUBMIT_LAPORAN")
    val isSubmitlaporan : Boolean,
    @SerializedName("IS_REVIEW")
    val isReview : Boolean
) : Parcelable
