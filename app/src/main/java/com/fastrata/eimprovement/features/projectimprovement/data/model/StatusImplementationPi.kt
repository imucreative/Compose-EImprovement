package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatusImplementationPi(val sudah : Sudah? = null, val akan : Akan? = null) : Parcelable

@Parcelize
data class Sudah(
    val Checked: Int,
    val periode_implementasi : PeriodeImplemen? = null
): Parcelable

@Parcelize
data class Akan(
    val Checked: Int,
    val identifikasi_masalah : IdentifikasiMasalah? = null,
    val analisa_data : AnalisaData? = null,
    val analisa_akar_masalah : AnalisaAkarMasalah? = null,
    val menyusun_rencana : MenyusunRencana? = null,
    val implementasi_rencana : ImplementasiRencana? = null,
    val analis_periksa_evaluasi : AnalisPeriksaEvaluasi? = null
): Parcelable


@Parcelize
data class PeriodeImplemen(
    val dari : String,
    val sampai: String
): Parcelable


@Parcelize
data class IdentifikasiMasalah(
    val dari : String,
    val sampai: String
): Parcelable

@Parcelize
data class AnalisaData(
    val dari : String,
    val sampai: String
): Parcelable

@Parcelize
data class AnalisaAkarMasalah(
    val dari : String,
    val sampai: String
): Parcelable

@Parcelize
data class MenyusunRencana(
    val dari : String,
    val sampai: String
): Parcelable

@Parcelize
data class ImplementasiRencana(
    val dari : String,
    val sampai: String
): Parcelable

@Parcelize
data class AnalisPeriksaEvaluasi(
    val dari : String,
    val sampai: String
): Parcelable