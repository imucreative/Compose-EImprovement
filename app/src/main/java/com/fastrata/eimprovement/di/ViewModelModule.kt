package com.fastrata.eimprovement.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fastrata.eimprovement.features.approval.ui.ListApprovalViewModel
import com.fastrata.eimprovement.features.changespoint.ui.ChangesPointCreateViewModel
import com.fastrata.eimprovement.features.changespoint.ui.create.ChangesRewardViewModel
import com.fastrata.eimprovement.features.login.ui.LoginViewModel
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementViewModel
import com.fastrata.eimprovement.features.suggestionsystem.ui.SuggestionSystemViewModel
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SsCreateAttachmentViewModel
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SsCreateTeamMemberViewModel
import com.fastrata.eimprovement.featuresglobal.viewmodel.CategoryViewModel
import com.fastrata.eimprovement.featuresglobal.viewmodel.StatusProposalViewModel
import com.fastrata.eimprovement.featuresglobal.viewmodel.TeamMemberViewModel
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
    @ViewModelKey(ChangesPointCreateViewModel::class)
    abstract fun bindListChangesPointViewModel(createViewModel: ChangesPointCreateViewModel): ViewModel

    // CP Reward
    @Binds
    @IntoMap
    @ViewModelKey(ChangesRewardViewModel::class)
    abstract fun bindListChangesPointRewardViewModel(viewModel: ChangesRewardViewModel): ViewModel

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

    // Master Category
    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    abstract fun bindListMasterDataCategoryViewModel(viewModel: CategoryViewModel): ViewModel

    // Master Team Member
    @Binds
    @IntoMap
    @ViewModelKey(TeamMemberViewModel::class)
    abstract fun bindListMasterDataTeamMemberViewModel(viewModel: TeamMemberViewModel): ViewModel

    // Master Status Proposal
    @Binds
    @IntoMap
    @ViewModelKey(StatusProposalViewModel::class)
    abstract fun bindListMasterDataStatusProposalViewModel(viewModel: StatusProposalViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
