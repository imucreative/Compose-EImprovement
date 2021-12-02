package com.fastrata.eimprovement.features.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.facebook.stetho.server.http.HttpStatus
import com.fastrata.eimprovement.BuildConfig
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
import com.google.android.material.snackbar.Snackbar
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
            version.text = "Version ${BuildConfig.VERSION_NAME}"
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
            HelperNotification().displayNoInternet(this,object : HelperNotification.CallbackRetry{
                override fun onRetry() {

                }
            })
        } else {
            try {
                viewModel.processLogin(username, password)
                viewModel.doLiveLogin.observeEvent(this) { loginObserver ->
                    loginObserver.observe(this, { result ->
                        when (result.status) {
                            Result.Status.LOADING -> {
                                Timber.d("###-- LOADING")
                                HelperLoading.displayLoadingWithText(this@LoginActivity, "", false)
                            }

                            Result.Status.SUCCESS -> {
                                Timber.d("###-- SUCCESS")
                                HelperLoading.hideLoading()

                                if (result.data?.code == HttpStatus.HTTP_OK) {
                                    // saved into hawk
                                    val loginEntity = LoginEntity(
                                        USER_ID = result.data.data[0].USER_ID,
                                        ROLE_ID = result.data.data[0].ROLE_ID,
                                        ROLE_NAME = result.data.data[0].ROLE_NAME,
                                        NIK = result.data.data[0].NIK,
                                        USER_NAME = result.data.data[0].USER_NAME,
                                        FULL_NAME = result.data.data[0].FULL_NAME,
                                        DIRECT_MANAGER_ID = result.data.data[0].DIRECT_MANAGER_ID,
                                        DIRECT_MANAGER = result.data.data[0].DIRECT_MANAGER,
                                        EMAIL = result.data.data[0].EMAIL,
                                        ORG_ID = result.data.data[0].ORG_ID,
                                        WAREHOUSE_ID = result.data.data[0].WAREHOUSE_ID,
                                        BRANCH_CODE = result.data.data[0].BRANCH_CODE,
                                        BRANCH = result.data.data[0].BRANCH,
                                        SUB_BRANCH = result.data.data[0].SUB_BRANCH,
                                        DEPARTMENT_ID = result.data.data[0].DEPARTMENT_ID,
                                        DEPARTMENT = result.data.data[0].DEPARTMENT,
                                        POSITION_ID = result.data.data[0].POSITION_ID,
                                        POSITION = result.data.data[0].POSITION,
                                        JOB_TITLE_ID = result.data.data[0].JOB_TITLE_ID,
                                        JOB_TITLE = result.data.data[0].JOB_TITLE,
                                        TOKEN = result.data.data[0].TOKEN,
                                        API_KEY = result.data.data[0].API_KEY,
                                        ROLES = result.data.data[0].ROLES,
                                    )
                                    HawkUtils().setDataLogin(loginEntity = loginEntity)
                                    HawkUtils().setStatusLogin(true)

                                    goToHomePage()
                                    //subscribeMasterCustomers(binding)
                                } else {
                                    result.data?.let {
                                        notification.showErrorDialog(
                                            this@LoginActivity,
                                            resources.getString(R.string.error),
                                            it.message
                                        )
                                    }
                                }
                            }
                            Result.Status.ERROR -> {
                                Timber.d("###-- ERROR")
                                HelperLoading.hideLoading()
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Error : ${result.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                                notification.showErrorDialog(
                                    this@LoginActivity,
                                    resources.getString(R.string.error),
                                    result.message.toString()
                                )
                            }
                        }
                    })
                }
            }catch (err: Exception){
                Toast.makeText(this,"Failed login",Toast.LENGTH_SHORT).show()
                Timber.e("### Error login : ${err.message}")
            }
        }
    }

    override fun onBackPressed() {
    }

}
