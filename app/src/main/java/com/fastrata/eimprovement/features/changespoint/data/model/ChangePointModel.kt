package com.fastrata.eimprovement.features.changespoint.data.model

import android.os.Parcelable
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposalItem
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChangePointModel(
    @SerializedName("RP_H_ID")
    val idCp: Int,
    @SerializedName("RP_NO")
    val cpNo: String,
    @SerializedName("USER_ID")
    val userId: Int,
    @SerializedName("STATUS_PROPOSAL")
    val status: StatusProposalItem,
    @SerializedName("BRANCH")
    val branch: String,
    @SerializedName("SUBBRANCH")
    val subBranch: String,
    @SerializedName("CREATED_DATE")
    val date: String,
    @SerializedName("CREATED_BY")
    val created : String,
    @SerializedName("TOTAL")
    val total: Int,
    @SerializedName("IS_EDIT")
    val isEdit: Boolean,
    @SerializedName("IS_DELETE")
    val isDelete: Boolean,
    @SerializedName("IS_VIEW")
    val isView: Boolean,
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
):Parcelable
