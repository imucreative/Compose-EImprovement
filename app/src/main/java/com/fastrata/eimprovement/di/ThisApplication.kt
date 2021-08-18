package com.fastrata.eimprovement.di

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.orhanobut.hawk.Hawk

class ThisApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        Hawk.init(this).build()
    }
}