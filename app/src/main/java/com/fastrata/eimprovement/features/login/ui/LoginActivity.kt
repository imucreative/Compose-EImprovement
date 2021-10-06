package com.fastrata.eimprovement.features.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.facebook.stetho.server.http.HttpStatus
import com.fastrata.eimprovement.HomeActivity
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.databinding.ActivityLoginBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.login.data.model.LoginEntity
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
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
                doLogin(binding)
            }
        }

        Tools.setSystemBarColor(this, R.color.colorMainEImprovement, this)
        Tools.setSystemBarLight(this)
    }

    private fun goToHomePage() {
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
//                            binding.progressbar.progressbar.visibility = View.VISIBLE
                            HelperLoading.displayLoadingWithText(this@LoginActivity,"",false)
                        }

                        Result.Status.SUCCESS -> {
                            Timber.d("###-- SUCCESS")
//                            binding.progressbar.progressbar.visibility = View.GONE
                            HelperLoading.hideLoading()

                            if (result.data?.code == HttpStatus.HTTP_OK) {
                                // saved into hawk
                                val loginEntity = LoginEntity(
                                    USER_ID = result.data.data[0].USER_ID,
                                    USER_NAME = result.data.data[0].USER_NAME,
                                    TOKEN = result.data.data[0].TOKEN,
                                    API_KEY = result.data.data[0].API_KEY,
                                    ROLES = result.data.data[0].ROLES,
                                    NIK = "11210012",
                                    BRANCH = "PUSAT",
                                    SUB_BRANCH = "FBPST - Gd Barang Dagang",
                                    DEPARTMENT = "ICT",
                                    POSITION = "STAFF",
                                    DIRECT_MANAGER = "Pak Ahmad",
                                    SALDO = "50000"
                                )
                                HawkUtils().setDataLogin(loginEntity = loginEntity)
                                HawkUtils().setStatusLogin(true)

                                goToHomePage()
                                //subscribeMasterCustomers(binding)
                            } else {
                                result.data?.let {
                                    notification.showErrorDialog(this@LoginActivity,resources.getString(R.string.error), it.message)
                                }
                            }
                        }

                        Result.Status.ERROR -> {
                            Timber.d("###-- ERROR")
//                            binding.progressbar.progressbar.visibility = View.GONE
                            HelperLoading.hideLoading()
                            notification.showErrorDialog(this@LoginActivity,resources.getString(R.string.error), result.message.toString())
                        }
                    }
                })
            }
        }
    }


}
