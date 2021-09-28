package com.fastrata.eimprovement.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AttachmentItem(
    var uri: String,
    var name: String,
    var size: String
) : Parcelable