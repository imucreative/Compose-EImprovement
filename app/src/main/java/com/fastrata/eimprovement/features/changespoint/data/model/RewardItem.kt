package com.fastrata.eimprovement.features.changespoint.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RewardItem(
    @SerializedName("GIFT_ID")
    val hadiahId: Int,
    @SerializedName("GIFT_NAME")
    val hadiah: String,
    @SerializedName("AMOUNT")
    val nilai: Int,
    @SerializedName("REMARK")
    val keterangan: String
):Parcelable