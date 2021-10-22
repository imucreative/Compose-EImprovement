package com.fastrata.eimprovement.features.changespoint.data.model

import android.os.Parcelable
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposalItem
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChangePointModel(
    @SerializedName("RP_H_ID")
    val idCp: Int,
    @SerializedName("NO_REEDEM_POINT")
    val cpNo: String,
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
    val total: String
):Parcelable
