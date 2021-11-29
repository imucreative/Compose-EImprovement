package com.fastrata.eimprovement.di

import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SuggestionSystemCreateWizard
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class SuggestionSystemCreateWizardModule {
    @ContributesAndroidInjector(modules = [SuggestionSystemFragmentBuildersModule::class])
    abstract fun contributeSuggestionSystemCreateWizard(): SuggestionSystemCreateWizard
}
