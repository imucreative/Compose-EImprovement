package com.fastrata.eimprovement.featuresglobal.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryImprovementItem(
    @SerializedName("CATEGORY_ID")
    var id: Int,
    @SerializedName("CATEGORY_NAME")
    var category: String,
    @SerializedName("CHECKED")
    var checked: Boolean
) : Parcelable
