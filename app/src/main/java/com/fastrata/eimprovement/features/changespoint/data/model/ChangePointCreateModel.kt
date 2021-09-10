package com.fastrata.eimprovement.features.changespoint.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChangePointCreateModel(
    var saldo : Int?,
    var no_penukaran : String?,
    var nama_pembuat: String?,
    var nik : String?,
    var cabang : String?,
    var department : String?,
    var jabatan : String?,
    var tgl_penukaran : String?,
    var keterangan: String?,
    var penukaran_hadiah : ArrayList<ChangePointRewardItem?>?,
    var riwayat : ArrayList<RiwayatItem?>?

): Parcelable