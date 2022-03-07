package com.fastrata.eimprovement.features.dashboard.ui.data

import android.os.Parcelable
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposalItem
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CalendarDashboardModel(
    @SerializedName("DOC_ID")
    var docId: Int,
    @SerializedName("DOC_NO")
    var docNo: String,
    @SerializedName("DOC_TYPE")
    var docType: String,
    @SerializedName("START_DATE")
    var startDate: String,
    @SerializedName("END_DATE")
    var endDate: String,
    @SerializedName("CREATED_DATE")
    var createdDate: String,
    @SerializedName("GET_MONTH")
    var getMonth: Int,
    @SerializedName("GET_DATE")
    var getDate: Int,
    @SerializedName("STATUS_PROPOSAL")
    val status: StatusProposalItem,
    @SerializedName("TITLE")
    var title: String,
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