package com.fastrata.eimprovement.di

import com.fastrata.eimprovement.features.approval.ui.ListApprovalHistoryStatusCpFragment
import com.fastrata.eimprovement.features.changespoint.ui.create.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ChangesPointFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeChangesPoint1Fragment(): ChangesPointStep1Fragment

    @ContributesAndroidInjector
    abstract fun contributeChangesPoint2Fragment(): ChangesPointStep2Fragment

    @ContributesAndroidInjector
    abstract fun contributeListApprovalHistoryStatusFragment(): ListApprovalHistoryStatusCpFragment

}
