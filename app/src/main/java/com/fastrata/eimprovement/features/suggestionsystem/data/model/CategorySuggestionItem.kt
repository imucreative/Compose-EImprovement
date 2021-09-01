package com.fastrata.eimprovement.features.suggestionsystem.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategorySuggestionItem(
    var id: Int,
    var category: String,
    var checked: Boolean
) : Parcelable
