package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AkarMasalahItem(
    val akarmslsh:String,
    val improvement: String,
    val detail : String
): Parcelable