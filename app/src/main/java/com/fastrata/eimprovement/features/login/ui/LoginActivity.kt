package com.fastrata.eimprovement.features.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.facebook.stetho.server.http.HttpStatus
import com.fastrata.eimprovement.HomeActivity
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.databinding.ActivityLoginBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.PreferenceUtils
import com.fastrata.eimprovement.utils.Tools.hideKeyboard
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), Injectable {

    private lateinit var notification: HelperNotification
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)
        viewModel = injectViewModel(viewModelFactory)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        notification = HelperNotification()

        binding.apply {
            btnLogin.setOnClickListener {
                /*UserName = username.text.toString()
                Password =password.text.toString()
                Log.i("user/pass :",UserName+"/"+Password)
                if (UserName== "" || Password == ""){
                    notification.showErrorDialog(this@LoginActivity,"Error","Silahkan masukan Username/Password Anda")
                }else{
                    SendData()
                }*/
                doLogin(binding)
            }
        }

        /*val savedLogin = PreferenceUtils(this).get(PREF_USER_NAME, "", true) ?: ""
        if (savedLogin.isNotEmpty()) {
            goToHomePage()
        }*/

        Tools.setSystemBarColor(this, R.color.colorMainEImprovement, this)
        Tools.setSystemBarLight(this)
    }

    /*private fun SendData() {
        val req = HashMap<String, String>()
        req["user_name"] = UserName
        req["user_password"] = Password
        req["device_uid"] = Tools.getDeviceID(this)
        req["device_name"] = Tools.deviceName
        Log.i("datanya yg dikirim", Gson().toJson(req))
        goToHomePage()
    }*/

    private fun goToHomePage() {
        HawkUtils().setSaldo("50000")
        HawkUtils().setLoginBoolean(true)
        startActivity(Intent(applicationContext, HomeActivity::class.java))
        this.finish()
    }

    private fun doLogin(binding: ActivityLoginBinding) {
        val username = binding.edtUserName.text.toString()
        val password = binding.edtPassword.text.toString()
        this.hideKeyboard()

        if (username.isEmpty() || password.isEmpty()) {
            SnackBarCustom.snackBarIconInfo(
                binding.root, layoutInflater, resources, this,
                "Username and Password cannot be empty",
                R.drawable.ic_close, R.color.red_500
            )
        } else if (!ConnectivityUtil.isConnected(this)) {
            SnackBarCustom.snackBarIconInfo(
                binding.root, layoutInflater, resources, this,
                "Please check your network connectivity",
                R.drawable.ic_close, R.color.red_500
            )
        } else {
            viewModel.processLogin(username, password)
            viewModel.doLiveLogin.observeEvent(this) { loginObserver ->
                loginObserver.observe(this, { result ->
                    when (result.status) {
                        Result.Status.LOADING -> {
                            Timber.d("###-- LOADING")
                            binding.progressbar.progressbar.visibility = View.VISIBLE
                        }

                        Result.Status.SUCCESS -> {
                            Timber.d("###-- SUCCESS")
                            binding.progressbar.progressbar.visibility = View.GONE

                            if (result.data?.code == HttpStatus.HTTP_OK) {
                                //saved into shared pref
                                PreferenceUtils(this).set(PREF_USER_NAME, result.data.data[0].USER_NAME, true)
                                PreferenceUtils(this).set(PREF_USER_ID, result.data.data[0].USER_ID, true)
                                PreferenceUtils(this).set(PREF_TOKEN, result.data.data[0].TOKEN, true)
                                PreferenceUtils(this).set(PREF_API_KEY, result.data.data[0].API_KEY, true)
                                PreferenceUtils(this).set(PREF_ROLES, result.data.data[0].ROLES, true)

                                goToHomePage()
                                //subscribeMasterCustomers(binding)
                            } else {
                                result.data?.let {
                                    notification.showErrorDialog(this@LoginActivity,"Error", it.message)
                                }
                            }
                        }

                        Result.Status.ERROR -> {
                            Timber.d("###-- ERROR")
                            binding.progressbar.progressbar.visibility = View.GONE
                            notification.showErrorDialog(this@LoginActivity,"Error", result.message.toString())
                        }
                    }
                })
            }
        }
    }


}
