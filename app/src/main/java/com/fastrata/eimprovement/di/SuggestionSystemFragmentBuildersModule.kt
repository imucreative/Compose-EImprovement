package com.fastrata.eimprovement.di

import com.fastrata.eimprovement.features.approval.ui.ListApprovalHistoryStatusSsFragment
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.*
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

    @ContributesAndroidInjector
    abstract fun contributeSuggestionSystem5Fragment(): SuggestionSystemStep5Fragment

    @ContributesAndroidInjector
    abstract fun contributeListApprovalHistoryStatusFragment(): ListApprovalHistoryStatusSsFragment
}
