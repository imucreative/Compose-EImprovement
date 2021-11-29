package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatusImplementationPiModel(
    @SerializedName("DONE")
    val sudah : StatusImplementationPiDoneModel? = null,
    @SerializedName("WANT")
    val akan : StatusImplementationPiWantModel? = null
) : Parcelable
