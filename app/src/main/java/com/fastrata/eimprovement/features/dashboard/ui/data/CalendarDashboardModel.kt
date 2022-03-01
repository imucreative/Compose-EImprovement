package com.fastrata.eimprovement.features.dashboard.ui.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CalendarDashboardModel(
    @SerializedName("DOC_ID")
    var DOC_ID: Int,
    @SerializedName("DOC_NO")
    var DOC_NO: String,
    @SerializedName("DOC_TYPE")
    var DOC_TYPE: String,
    @SerializedName("START_DATE")
    var START_DATE: String,
    @SerializedName("END_DATE")
    var END_DATE: String,
    @SerializedName("CREATED_DATE")
    var CREATED_DATE: String,
    @SerializedName("GET_MONTH")
    var GET_MONTH: Int,
    @SerializedName("GET_DATE")
    var GET_DATE: Int,
    @SerializedName("STATUS_PROP")
    var STATUS_PROP: Int,
    @SerializedName("STATUS_PROPOSAL_NAME")
    var STATUS_PROPOSAL_NAME: String,
    @SerializedName("TITLE")
    var TITLE: String,
): Parcelable