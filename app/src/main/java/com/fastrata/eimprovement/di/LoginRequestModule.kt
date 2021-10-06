package com.fastrata.eimprovement.di

import com.fastrata.eimprovement.features.login.data.model.LoginRemoteRequest
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class LoginRequestModule {
    @ContributesAndroidInjector
    abstract fun contributeLoginRequest(): LoginRemoteRequest
}
