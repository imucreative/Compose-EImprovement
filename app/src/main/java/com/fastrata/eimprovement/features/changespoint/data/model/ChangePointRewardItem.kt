package com.fastrata.eimprovement.features.changesPoint.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChangePointRewardItem(
    val no : Int?,
    val hadiah: String,
    val nilai: String,
    val keterangan: String
):Parcelable

@Parcelize
data class hadiahItem(
    var hadiah: String
) : Parcelable
