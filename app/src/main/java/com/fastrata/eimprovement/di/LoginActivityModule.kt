package com.fastrata.eimprovement.di

import com.fastrata.eimprovement.features.login.ui.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class LoginActivityModule {
    @ContributesAndroidInjector(modules = [LoginRequestModule::class])
    abstract fun contributeLoginActivity(): LoginActivity
}