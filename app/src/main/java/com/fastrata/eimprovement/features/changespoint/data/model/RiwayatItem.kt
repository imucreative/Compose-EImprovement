package com.fastrata.eimprovement.features.changespoint.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RiwayatItem (
            val no: Int,
            val pic : String,
            val status : String,
            val komentar : String,
            val tanggal : String
        ):Parcelable
