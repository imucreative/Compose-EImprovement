package com.fastrata.eimprovement.features.approval.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApprovalHistoryStatusModel(
    @SerializedName("FULL_NAME")
    val pic: String,
    @SerializedName("ACTION_MESSAGE")
    val status: String,
    @SerializedName("COMMENT_MESSAGE")
    val comment: String,
    @SerializedName("CREATED_DATE")
    val date: String
): Parcelable

