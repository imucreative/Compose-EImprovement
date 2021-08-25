package com.fastrata.eimprovement.di

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.fastrata.eimprovement.BuildConfig
import com.orhanobut.hawk.Hawk
import timber.log.Timber

class ThisApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        Hawk.init(this).build()

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
    }
}