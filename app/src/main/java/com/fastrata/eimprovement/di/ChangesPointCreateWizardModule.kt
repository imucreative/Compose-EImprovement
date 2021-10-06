package com.fastrata.eimprovement.di

import com.fastrata.eimprovement.features.changespoint.ui.create.ChangesPointCreateWizard
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ChangesPointCreateWizardModule {
    @ContributesAndroidInjector(modules = [ChangesPointFragmentBuildersModule::class])
    abstract fun contributeChangesPointCreateWizard(): ChangesPointCreateWizard
}
