package com.fastrata.eimprovement.features.changespoint.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RewardItem(
    val no : Int?,
    val hadiah: String,
    val nilai: String,
    val keterangan: String
):Parcelable