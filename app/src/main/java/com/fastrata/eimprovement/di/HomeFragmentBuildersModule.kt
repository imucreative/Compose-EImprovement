package com.fastrata.eimprovement.di

import com.fastrata.eimprovement.features.approval.ui.ListApprovalFragment
import com.fastrata.eimprovement.features.changespoint.ui.ChangesPointFragment
import com.fastrata.eimprovement.features.dashboard.ui.DashboardFragment
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementFragment
import com.fastrata.eimprovement.features.settings.ui.ChangePasswordFragment
import com.fastrata.eimprovement.features.settings.ui.SettingsFragment
import com.fastrata.eimprovement.features.settings.ui.mutasi.MutasiFragment
import com.fastrata.eimprovement.features.suggestionsystem.ui.SuggestionSystemFragment
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
    abstract fun contributeMutasiFragment() : MutasiFragment

    @ContributesAndroidInjector
    abstract fun contributeChangePasswordFragment(): ChangePasswordFragment

    @ContributesAndroidInjector
    abstract fun contributeListApprovalFragment(): ListApprovalFragment

    @ContributesAndroidInjector
    abstract fun contributeSuggestionSystemFragment(): SuggestionSystemFragment

    @ContributesAndroidInjector
    abstract fun contributeProjectImprovementFragment(): ProjectImprovementFragment

    @ContributesAndroidInjector
    abstract fun contributeChangePointFragment(): ChangesPointFragment

}
