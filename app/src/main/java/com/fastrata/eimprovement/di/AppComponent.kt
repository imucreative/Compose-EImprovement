package com.fastrata.eimprovement.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        HomeActivityModule::class,
        LoginActivityModule::class,
        SuggestionSystemCreateWizardModule::class,
        ProjectImprovementCreateWizardModule::class,
        ChangesPointCreateWizardModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: ThisApplication)
}
