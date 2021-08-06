package com.fastrata.eimprovement.features.splashscreen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker
import com.fastrata.eimprovement.features.utils.PreferenceUtils
import com.fastrata.eimprovement.features.utils.SnackBarCustom
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.features.login.ui.LoginActivity
import com.fastrata.eimprovement.features.utils.Tools
import timber.log.Timber

class SplashScreenActivity : AppCompatActivity() {

    private val _permissionCode = 1000
    private var parentView: View? = null

    private lateinit var preferenceUtils: PreferenceUtils
    private lateinit var welcomeMessageModel: WelcomeMessageModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        parentView = findViewById(android.R.id.content)

        requestAllPermissions()
        preferenceUtils = PreferenceUtils(this)
        welcomeMessageModel = preferenceUtils.getWelcomeMessage()

        Tools.setSystemBarColor(this, R.color.colorMainEImprovement, this)
        Tools.setSystemBarLight(this)
    }

    private fun processSplashScreen() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({

            if (!welcomeMessageModel.isDisplay) {
                goToWelcomeMessage()
            } else {
                goToLoginPage()
            }
            /*
            val savedLogin = PreferenceUtil(this).get(PREF_USER_NAME, "", true) ?: ""
            if (savedLogin.isNotEmpty()) {
                //get master customer
                //subscribeMasterCustomers(savedLogin)
                goDashboardPage()
            } else {
                //do postDelayed and go to login page
                goToLoginPage()
            }
             */
        }, 2000) //in millisecond
    }

    private fun goToWelcomeMessage() {
        startActivity(Intent(applicationContext, WelcomeMessageActivity::class.java))
        this.finish()
    }

    private fun goToLoginPage() {
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        this.finish()
    }

    private fun requestAllPermissions() {
        Timber.e("Request permission clicked!!!")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionChecker.checkSelfPermission(this, Manifest.permission.CAMERA) == PermissionChecker.PERMISSION_DENIED
                || PermissionChecker.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PermissionChecker.PERMISSION_DENIED
                || PermissionChecker.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PermissionChecker.PERMISSION_DENIED
                || PermissionChecker.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PermissionChecker.PERMISSION_DENIED
                || PermissionChecker.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PermissionChecker.PERMISSION_DENIED
            ) {

                //permission was not enabled
                val permission = arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE
                )

                //show popup to request permission
                requestPermissions(permission, _permissionCode)
            } else {
                //permission already granted
                SnackBarCustom.snackBarIconInfo(parentView!!, layoutInflater, resources, this, "Permission granted", R.drawable.ic_error_outline, R.color.blue_500)
                SnackBarCustom
                processSplashScreen()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //called when user presses ALLOW or DENY from Permission Request Popup
        when (requestCode) {
            _permissionCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup was granted
                    processSplashScreen()
                } else {
                    //permission from popup was denied
                    SnackBarCustom.snackBarIconInfo(parentView!!, layoutInflater, resources, this, "Permission denied", R.drawable.ic_close, R.color.red_500)
                }
            }
        }
    }

}
