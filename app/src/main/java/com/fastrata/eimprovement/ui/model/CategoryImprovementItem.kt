package com.fastrata.eimprovement.ui.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryImprovementItem(
    @SerializedName("id")
    var id: Int,
    @SerializedName("kategory")
    var category: String,
    @SerializedName("checked")
    var checked: Boolean
) : Parcelable
