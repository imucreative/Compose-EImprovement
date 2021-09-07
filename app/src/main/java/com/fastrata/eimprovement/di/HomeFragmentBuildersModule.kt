package com.fastrata.eimprovement.di

import com.fastrata.eimprovement.features.approval.ui.ListApprovalFragment
import com.fastrata.eimprovement.features.changesPoint.ui.ChangesPointFragment
import com.fastrata.eimprovement.features.dashboard.ui.DashboardFragment
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementFragment
import com.fastrata.eimprovement.features.settings.ui.SettingsFragment
import com.fastrata.eimprovement.features.suggestionsystem.ui.SuggestionSystemFragment
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SuggestionSystemCreateWizard
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SuggestionSystemStep1Fragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class HomeFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeDashboardFragment(): DashboardFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun contributeListApprovalFragment(): ListApprovalFragment

    @ContributesAndroidInjector
    abstract fun contributeSuggestionSystemFragment(): SuggestionSystemFragment

    @ContributesAndroidInjector
    abstract fun contributeProjectImprovementFragment(): ProjectImprovementFragment

    @ContributesAndroidInjector
    abstract fun contributeChangePointFragment(): ChangesPointFragment

}
