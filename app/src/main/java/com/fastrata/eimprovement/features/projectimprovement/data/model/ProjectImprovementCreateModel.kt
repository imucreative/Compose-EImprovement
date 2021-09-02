package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import com.fastrata.eimprovement.features.suggestionsystem.data.model.AttachmentItem
import com.fastrata.eimprovement.features.suggestionsystem.data.model.CategorySuggestionItem
import com.fastrata.eimprovement.features.suggestionsystem.data.model.StatusImplementation
import com.fastrata.eimprovement.features.suggestionsystem.data.model.TeamMemberItem

import kotlinx.parcelize.Parcelize

@Parcelize
data class ProjectImprovementCreateModel(
    var piNo: String?,
    var date: String?,
    var title: String?,
    var categorySuggestion: ArrayList<CategorySuggestionItem?>?,
    var branch: String?,
    var subBranch: String?,
    var department: String?,
    var year: String?,
    var statusImplementation: StatusImplementation? = null,
    var indenmasalah: String?,
    var settarget: String?,
    var akarmasalah: ArrayList<AkarMasalahItem?>?,
    var sebabmasalah: ArrayList<SebabMasalahItem?>?,
    var attachment: ArrayList<AttachmentItem?>?,
    var teammember: ArrayList<TeamMemberItem?>?
) : Parcelable