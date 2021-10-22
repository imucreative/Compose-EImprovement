package com.fastrata.eimprovement.features.settings.ui.changepassword.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChangePasswordModel (
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("user_password")
    val userPassword: String,
    @SerializedName("new_password")
    val newPassword: String
    ):Parcelable