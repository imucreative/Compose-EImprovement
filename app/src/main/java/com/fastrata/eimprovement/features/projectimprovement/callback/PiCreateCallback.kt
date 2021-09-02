package com.fastrata.eimprovement.features.projectimprovement.callback

import com.fastrata.eimprovement.features.suggestionsystem.data.model.AttachmentItem
import com.fastrata.eimprovement.features.suggestionsystem.data.model.CategorySuggestionItem
import com.fastrata.eimprovement.features.suggestionsystem.data.model.TeamMemberItem

interface ProjecImprovementSystemCreateCallback {
    fun onDataPass(): Boolean
}

interface ProjecImprovementCreateTeamMemberCallback {
    fun removeClicked(data: TeamMemberItem)
}

interface ProjecImprovementCreateAttachmentCallback {
    fun removeClicked(data: AttachmentItem)
    fun showAttachment(data: AttachmentItem)
}

interface ProjecImprovementCreateCategorySuggestionCallback {
    fun checkClicked(data: CategorySuggestionItem, checked: Boolean)
}