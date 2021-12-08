package com.fastrata.eimprovement.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class MessageItem (
        @SerializedName("ID")
        val id : Int,
        @SerializedName("TYPE_PROPOSAL")
        val type: String,
        @SerializedName("DOC_ID")
        val doc:String,
        @SerializedName("MESSAGE")
        val message: String
        ):Parcelable