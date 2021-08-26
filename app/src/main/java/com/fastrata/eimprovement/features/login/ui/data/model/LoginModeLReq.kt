package com.fastrata.eimprovement.features.login.ui.data.model

data class LoginModeLReq(
    val user_name: String,
    val password: String,
    val device_uid: String,
    val device_name: String,
)