package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AkarMasalahItem(
    val sequence : Int,
    val kenapa:String,
    val aksi: String,
    val detail_langkah : String
): Parcelable