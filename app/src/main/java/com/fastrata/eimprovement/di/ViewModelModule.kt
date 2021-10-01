package com.fastrata.eimprovement.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fastrata.eimprovement.features.approval.ui.ListApprovalViewModel
import com.fastrata.eimprovement.features.changespoint.ui.ChangesPointCreateModel
import com.fastrata.eimprovement.features.changespoint.ui.create.ChangesPointRewardViewModel
import com.fastrata.eimprovement.features.login.ui.LoginViewModel
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementViewModel
import com.fastrata.eimprovement.features.suggestionsystem.ui.SuggestionSystemViewModel
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SsCreateAttachmentViewModel
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SsCreateCategorySuggestionViewModel
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SsCreateTeamMemberViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListApprovalViewModel::class)
    abstract fun bindListApprovalViewModel(viewModel: ListApprovalViewModel): ViewModel

    // SS
    @Binds
    @IntoMap
    @ViewModelKey(SuggestionSystemViewModel::class)
    abstract fun bindListSuggestionSystemViewModel(viewModel: SuggestionSystemViewModel): ViewModel

    // PI
    @Binds
    @IntoMap
    @ViewModelKey(ProjectImprovementViewModel::class)
    abstract fun bindListProjectImprovementViewModel(viewModel: ProjectImprovementViewModel): ViewModel

    // CP
    @Binds
    @IntoMap
    @ViewModelKey(ChangesPointCreateModel::class)
    abstract fun bindListChangesPointViewModel(createModel: ChangesPointCreateModel): ViewModel

    // CP Reward
    @Binds
    @IntoMap
    @ViewModelKey(ChangesPointRewardViewModel::class)
    abstract fun bindListChangesPointRewardViewModel(viewModel: ChangesPointRewardViewModel): ViewModel

    // SS Step 1
    @Binds
    @IntoMap
    @ViewModelKey(SsCreateCategorySuggestionViewModel::class)
    abstract fun bindListCategorySuggestionViewModel(viewModel: SsCreateCategorySuggestionViewModel): ViewModel
    // SS Step 3
    @Binds
    @IntoMap
    @ViewModelKey(SsCreateTeamMemberViewModel::class)
    abstract fun bindListTeamMemberViewModel(viewModel: SsCreateTeamMemberViewModel): ViewModel
    // SS Step 4
    @Binds
    @IntoMap
    @ViewModelKey(SsCreateAttachmentViewModel::class)
    abstract fun bindListAttachmentViewModel(viewModel: SsCreateAttachmentViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
