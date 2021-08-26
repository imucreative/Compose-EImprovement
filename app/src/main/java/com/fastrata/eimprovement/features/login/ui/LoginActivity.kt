package com.fastrata.eimprovement.features.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.fastrata.eimprovement.HomeActivity
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.ActivityLoginBinding
import com.fastrata.eimprovement.features.login.ui.data.model.LoginModeLReq
import com.fastrata.eimprovement.features.login.ui.data.model.LoginModelResp
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.HelperNotification
import com.fastrata.eimprovement.utils.Tools
import com.google.gson.Gson
import java.util.HashMap

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var notification: HelperNotification
    private lateinit var dataReq : LoginModeLReq
    private lateinit var dataResp : LoginModelResp
    var UserName: String = "";
    var Password: String = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        notification = HelperNotification()
        setContentView(binding.root)

        binding.apply {
            btnLogin.setOnClickListener {
                UserName = username.text.toString()
                Password =password.text.toString()
                Log.i("user/pass :",UserName+"/"+Password)
                if (UserName== "" || Password == ""){
                    notification.showErrorDialog(this@LoginActivity,"Error","Silahkan masukan Username/Password Anda")
                }else{
                    SendData()
                }
            }
        }

        Tools.setSystemBarColor(this, R.color.colorMainEImprovement, this)
        Tools.setSystemBarLight(this)
    }

    private fun SendData() {
        val req = HashMap<String, String>()
        req["user_name"] = UserName
        req["user_password"] = Password
        req["device_uid"] = Tools.getDeviceID(this)
        req["device_name"] = Tools.deviceName
        Log.i("datanya yg dikirim", Gson().toJson(req))
        GoToHome()
    }

    private fun GoToHome(){
        HawkUtils().setSaldo("50000")
        HawkUtils().setLoginBoolean(true)
        Intent(this@LoginActivity, HomeActivity::class.java).also {
                    startActivity(it)
        }
    }


}
