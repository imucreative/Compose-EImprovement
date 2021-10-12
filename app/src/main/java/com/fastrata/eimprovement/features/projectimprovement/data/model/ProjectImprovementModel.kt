package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposal
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProjectImprovementModel(
    @SerializedName("no_pi")
    val piNo: String,
    @SerializedName("status")
    val status: StatusProposal,
    @SerializedName("judul")
    val title: String,
    @SerializedName("kategori")
    val category: String,
    @SerializedName("cabang")
    val branch: String,
    @SerializedName("subcabang")
    val subBranch: String,
    @SerializedName("tgl_dibuat")
    val date : String,
    @SerializedName("dibuat_oleh")
    val createdBy : String
): Parcelable