package com.fastrata.eimprovement.features.dashboard.ui.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class BalanceModel (
    @SerializedName("TOTAL")
    val total: Int
        ):Parcelable