package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatusImplementationPiDoneModel(
    @SerializedName("STARTDATE_IMP")
    val from : String,
    @SerializedName("ENDDATE_IMP")
    val to: String
): Parcelable