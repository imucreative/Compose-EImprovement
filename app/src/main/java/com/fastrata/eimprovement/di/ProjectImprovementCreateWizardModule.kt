package com.fastrata.eimprovement.di

import com.fastrata.eimprovement.features.projectimprovement.ui.create.ProjectImprovementCreateWizard
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ProjectImprovementCreateWizardModule {
    @ContributesAndroidInjector(modules = [ProjectImprovementFragmentBuildersModule::class])
    abstract fun contributeProjectImprovementCreateWizard(): ProjectImprovementCreateWizard
}
