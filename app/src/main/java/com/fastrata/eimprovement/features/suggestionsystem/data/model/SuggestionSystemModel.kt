package com.fastrata.eimprovement.features.suggestionsystem.data.model

import android.os.Parcelable
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposalItem
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SuggestionSystemModel(
    @SerializedName("no_ss")
    val ssNo: String,
    @SerializedName("tgl_dibuat")
    val date: String,
    @SerializedName("judul")
    val title: String,
    @SerializedName("status")
    val status: StatusProposalItem,
    @SerializedName("kategori")
    val categoryRepairment: String,
    @SerializedName("cabang")
    val branch: String,
    @SerializedName("subcabang")
    val subBranch: String
) : Parcelable
