package com.fastrata.eimprovement.features.changespoint.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChangePointModel(
    val no_penukaran: String,
    val status: String,
    val tgl_input: String,
    val deskripsi: String,
    val cabang: String,
    val subcabang: String,
    val total: String
):Parcelable
