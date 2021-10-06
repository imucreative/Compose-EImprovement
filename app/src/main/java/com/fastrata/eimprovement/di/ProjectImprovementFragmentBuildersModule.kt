package com.fastrata.eimprovement.di

import com.fastrata.eimprovement.features.approval.ui.ListApprovalHistoryStatusPiFragment
import com.fastrata.eimprovement.features.projectimprovement.ui.create.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ProjectImprovementFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeProjectImprovement1Fragment(): ProjectImprovStep1Fragment

    @ContributesAndroidInjector
    abstract fun contributeProjectImprovement2Fragment(): ProjectImprovStep2Fragment

    @ContributesAndroidInjector
    abstract fun contributeProjectImprovement3Fragment(): ProjectImprovStep3Fragment

    @ContributesAndroidInjector
    abstract fun contributeProjectImprovement4Fragment(): ProjectImprovStep4Fragment

    @ContributesAndroidInjector
    abstract fun contributeProjectImprovement5Fragment(): ProjectImprovStep5Fragment

    @ContributesAndroidInjector
    abstract fun contributeProjectImprovement6Fragment(): ProjectImprovStep6Fragment

    @ContributesAndroidInjector
    abstract fun contributeProjectImprovement7Fragment(): ProjectImprovStep7Fragment

    @ContributesAndroidInjector
    abstract fun contributeProjectImprovement8Fragment(): ProjectImprovStep8Fragment

    @ContributesAndroidInjector
    abstract fun contributeProjectImprovement9Fragment(): ProjectImprovStep9Fragment

    @ContributesAndroidInjector
    abstract fun contributeListApprovalHistoryStatusFragment(): ListApprovalHistoryStatusPiFragment
}
