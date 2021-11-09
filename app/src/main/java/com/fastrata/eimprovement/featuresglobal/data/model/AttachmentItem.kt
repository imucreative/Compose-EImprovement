package com.fastrata.eimprovement.featuresglobal.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AttachmentItem(
    @SerializedName("ATTACH_ID")
    var id: Int,
    @SerializedName("FILE_NAME")
    var name: String,
    @SerializedName("MIME_TYPE")
    var type: String,
    @SerializedName("GROUP")
    var group: String,
    @SerializedName("CREATED_BY")
    var createdBy: String,
    @SerializedName("FILE_LOCATION")
    var fileLocation: String
) : Parcelable