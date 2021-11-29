package com.fastrata.eimprovement.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fastrata.eimprovement.features.approval.ui.ListApprovalViewModel
import com.fastrata.eimprovement.features.changespoint.ui.ChangesPointCreateViewModel
import com.fastrata.eimprovement.features.changespoint.ui.create.ChangesRewardViewModel
import com.fastrata.eimprovement.features.dashboard.ui.data.BalanceCreateViewModel
import com.fastrata.eimprovement.features.login.ui.LoginViewModel
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementViewModel
import com.fastrata.eimprovement.features.settings.ui.changepassword.data.model.ChangePasswordCreateViewModel
import com.fastrata.eimprovement.features.settings.ui.mutasi.MutasiViewCreateModel
import com.fastrata.eimprovement.features.suggestionsystem.ui.SuggestionSystemViewModel
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SsCreateAttachmentViewModel
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SsCreateTeamMemberViewModel
import com.fastrata.eimprovement.featuresglobal.viewmodel.*
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

    // Master Check Period
    @Binds
    @IntoMap
    @ViewModelKey(CheckPeriodViewModel::class)
    abstract fun bindCheckPeriodViewModel(viewModel: CheckPeriodViewModel): ViewModel

    // Master Branch
    @Binds
    @IntoMap
    @ViewModelKey(BranchViewModel::class)
    abstract fun bindListMasterDataBranchViewModel(viewModel: BranchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BalanceCreateViewModel::class)
    abstract fun bindViewBalanceViewModel(viewModel: BalanceCreateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MutasiViewCreateModel::class)
    abstract fun bindViewMutasiFragmentViewModel(viewModel: MutasiViewCreateModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChangePasswordCreateViewModel::class)
    abstract fun bindViewChangePasswordViewModel(viewModel : ChangePasswordCreateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AttachmentViewModel::class)
    abstract fun bindAttachmentViewModel(viewModel : AttachmentViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
