package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class categoryFixingItem (
        val peningkatan_profit_checked : Int,
        val efisiensi_biaya : Int
        ):Parcelable