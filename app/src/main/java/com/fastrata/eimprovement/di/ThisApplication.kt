package com.fastrata.eimprovement.di

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.IdRes
import androidx.multidex.MultiDex
import com.facebook.stetho.Stetho
import dagger.android.DispatchingAndroidInjector
import com.fastrata.eimprovement.BuildConfig
import com.fastrata.eimprovement.api.AMQPConsumer
import com.fastrata.eimprovement.utils.HawkUtils
import com.orhanobut.hawk.Hawk
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import java.net.URL
import javax.inject.Inject

class ThisApplication : Application(), HasAndroidInjector {
    var context: Context? = null

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    //override fun activityInjector() = dispatchingAndroidInjector

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        Hawk.init(this).build()

        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)
        AppInjector.init(this)

        if (BuildConfig.DEBUG) {
            // https://ayusch.com/timber-for-android/
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return String.format(
                        "#Class:%s, #Method:%s, #Line:%s ->",
                        super.createStackElementTag(element),
                        element.methodName,
                        element.lineNumber
                    )
                }
            })
        }

        startAMQPConsumer(this)
        val intent = Intent(context, ThisApplication::class.java)
//        if (intent.hasExtra("message")){
//            correctivePlanOnClickListener()
//        }

        /*//val userName = PreferenceUtils(this).get(PREF_USER_NAME, "", true) ?: ""
        val userName = HawkUtils().getDataLogin().USER_NAME

        //don't start sync if user does not login yet
        if (userName.isBlank()) return*/
    }

    private fun correctivePlanOnClickListener(){

    }

    private fun changeDestinationFragment(@IdRes containerViewId : Int,title:String){

    }


    companion object HomeSingleton {
        private var amqpConsumer: AMQPConsumer? = null

        fun startAMQPConsumer(context: Context){
            val url: URL = URL(BuildConfig.BASE_URL)
            val amqpHost = url.host
            val amqpUser = "user"
            val amqpPassword = "user"
            val amqpPort = 81
            val amqpVhost = "/"
            val queueName = "EMP."+ HawkUtils().getDataLogin().USER_ID.toString()

            if (amqpConsumer != null) {
                if (amqpConsumer!!.isConnected()){
                    amqpConsumer!!.stop()
                }
                amqpConsumer = null
            }
            amqpConsumer = AMQPConsumer(
                amqpHost,
                amqpPort,
                amqpUser,
                amqpPassword,
                amqpVhost,
                Build.PRODUCT + " " + Build.MODEL, context
            );
            amqpConsumer!!.start(queueName)
        }

        fun stopAMQPConsumer(){
            amqpConsumer!!.stop()
        }
    }


}