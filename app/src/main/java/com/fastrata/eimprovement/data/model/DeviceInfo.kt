package com.fastrata.eimprovement.data.model

import java.io.Serializable

class DeviceInfo : Serializable {
    var device: String? = null
    var osVersion: String? = null
    var appVersion: String? = null
    var serial: String? = null
    var regid: String? = null
}