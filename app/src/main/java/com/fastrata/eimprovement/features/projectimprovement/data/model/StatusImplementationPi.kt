package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatusImplementationPi(val sudah : Sudah? = null, val akan : Akan? = null) : Parcelable

@Parcelize
data class Sudah(
    val from : String,
    val to: String
): Parcelable

@Parcelize
data class Akan(
    val identifikasiMasalah : IdentifikasiMasalah? = null,
    val analisaData : AnalisaData? = null,
    val analisaAkarMasalah : AnalisaAkarMasalah? = null,
    val menyusunRencana : MenyusunRencana? = null,
    val implementasiRencana : ImplementasiRencana? = null,
    val analisPeriksaEvaluasi : AnalisPeriksaEvaluasi? = null
): Parcelable

@Parcelize
data class IdentifikasiMasalah(
    val from : String,
    val to: String
): Parcelable

@Parcelize
data class AnalisaData(
    val from : String,
    val to: String
): Parcelable

@Parcelize
data class AnalisaAkarMasalah(
    val from : String,
    val to: String
): Parcelable

@Parcelize
data class MenyusunRencana(
    val from : String,
    val to: String
): Parcelable

@Parcelize
data class ImplementasiRencana(
    val from : String,
    val to: String
): Parcelable

@Parcelize
data class AnalisPeriksaEvaluasi(
    val from : String,
    val to: String
): Parcelable