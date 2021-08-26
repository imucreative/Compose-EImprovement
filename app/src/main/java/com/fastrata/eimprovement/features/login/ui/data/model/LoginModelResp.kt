package com.fastrata.eimprovement.features.login.ui.data.model

import java.util.*
import kotlin.collections.ArrayList

class LoginModelResp {
    var code = 0
    var success: Boolean? = false
    var message: String? = null
    var Data: data? = null

    class data {
        var user_id: String? = null
        var user_name : String? = null
        var token : String? = null
        var api_key : String? = null
        var roles: ArrayList<String>? = null
    }
}