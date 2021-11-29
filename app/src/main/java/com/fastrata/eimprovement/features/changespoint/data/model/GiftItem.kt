package com.fastrata.eimprovement.features.changespoint.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GiftItem(
    @SerializedName("GIFT_ID")
    var id: Int,
    @SerializedName("GIFT_NAME")
    var hadiah: String,
    @SerializedName("GIFT_VALUE")
    var nilai: Int
) : Parcelable
