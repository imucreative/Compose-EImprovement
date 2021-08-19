package com.fastrata.eimprovement.features.suggestionsystem.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatusImplementation(
    var status: String,
    var to: String,
    var from: String
) : Parcelable