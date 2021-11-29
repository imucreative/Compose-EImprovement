package com.fastrata.eimprovement.features.login.data.model

import com.google.gson.annotations.SerializedName

data class RoleEntity(
    @SerializedName("menu_code")
    val MENU_CODE: String,
    @SerializedName("submenu_code")
    val SUBMENU_CODE: String,
    @SerializedName("menu_name")
    val MENU_NAME: String,
    @SerializedName("submenu_name")
    val SUBMENU_NAME: String,
)