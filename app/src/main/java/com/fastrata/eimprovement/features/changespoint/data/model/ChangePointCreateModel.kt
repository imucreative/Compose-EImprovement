package com.fastrata.eimprovement.features.changespoint.data.model

import android.os.Parcelable
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposalItem
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChangePointCreateModel(
    @SerializedName("REEDEM_POINT_H_ID")
    var id : Int?,
    @SerializedName("TOTAL")
    var saldo : Int?,
    @SerializedName("NO_REEDEM_POINT")
    var cpNo : String?,
    @SerializedName("FULL_NAME")
    var name: String?,
    @SerializedName("NIK")
    var nik : String?,
    @SerializedName("USER_ID")
    var userId: Int?,
    @SerializedName("ORG_ID")
    var orgId: Int?,
    @SerializedName("WAREHOUSE_ID")
    var warehouseId: Int?,
    @SerializedName("HEAD_ID")
    var headId: Int?,
    @SerializedName("BRANCH")
    var branch : String?,
    @SerializedName("SUBBRANCH")
    var subBranch : String?,
    @SerializedName("DEPARTMENT")
    var department : String?,
    @SerializedName("JOBLEVEL_NAME")
    var position : String?,
    @SerializedName("INPUT_DATE")
    var date : String?,
    @SerializedName("DESCRIPTION")
    var description: String?,
    @SerializedName("REDEEM_GIFT")
    var reward : ArrayList<RewardItem?>?,
    @SerializedName("STATUS_PROPOSAL")
    var statusProposal: StatusProposalItem?
): Parcelable