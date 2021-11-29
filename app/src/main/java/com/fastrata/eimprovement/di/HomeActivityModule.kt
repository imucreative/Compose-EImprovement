package com.fastrata.eimprovement.di

import com.fastrata.eimprovement.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class HomeActivityModule {
    @ContributesAndroidInjector(modules = [HomeFragmentBuildersModule::class])
    abstract fun contributeHomeActivity(): HomeActivity
}
