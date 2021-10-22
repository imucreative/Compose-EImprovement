package com.fastrata.eimprovement.features.settings.ui.mutasi.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MutasiModel(
    @SerializedName("INPUT_DATE")
    val date: String,
    @SerializedName("REWARD_TYPE")
    val tipe: String,
    @SerializedName("DOC_NO")
    val document_no: String,
    @SerializedName("STATUS_PROPOSAL_NAME")
    val stat: String,
    @SerializedName("REMARK")
    val desc: String,
    @SerializedName("PEMBUAT")
    val created_by : String,
    @SerializedName("AMOUNT_DEBIT")
    val nominal_debit: Int,
    @SerializedName("AMOUNT_KREDIT")
    val nominal_kredit : Int
):Parcelable