package com.fastrata.eimprovement.featuresglobal.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SubBranchItem (
    @SerializedName("BRANCH_CODE")
    var branchCode: String,
    @SerializedName("WAREHOUSE_ID")
    var warehouseId: Int,
    @SerializedName("SUBBRANCH")
    var subBranch: String,
    @SerializedName("SUBBRANCH_NAME")
    var subBranchName: String,
): Parcelable