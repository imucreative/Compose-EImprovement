package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SebabMasalahItem(
    val pnybmslh: String,
    val w1: String,
    val w2: String,
    val w3: String,
    val w4: String,
    val w5: String,
    val akarmslsh : String
): Parcelable