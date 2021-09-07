package com.fastrata.eimprovement.di

import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SuggestionSystemStep1Fragment
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SuggestionSystemStep2Fragment
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SuggestionSystemStep3Fragment
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SuggestionSystemStep4Fragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class SuggestionSystemFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeSuggestionSystem1Fragment(): SuggestionSystemStep1Fragment

    @ContributesAndroidInjector
    abstract fun contributeSuggestionSystem2Fragment(): SuggestionSystemStep2Fragment

    @ContributesAndroidInjector
    abstract fun contributeSuggestionSystem3Fragment(): SuggestionSystemStep3Fragment

    @ContributesAndroidInjector
    abstract fun contributeSuggestionSystem4Fragment(): SuggestionSystemStep4Fragment
}
