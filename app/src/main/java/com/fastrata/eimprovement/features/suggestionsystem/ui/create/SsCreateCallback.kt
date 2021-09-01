package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import com.fastrata.eimprovement.features.suggestionsystem.data.model.*

interface SuggestionSystemCreateCallback {
    fun onDataPass(): Boolean
}

interface SuggestionSystemCreateTeamMemberCallback {
    fun removeClicked(data: TeamMemberItem)
}

interface SuggestionSystemCreateAttachmentCallback {
    fun removeClicked(data: AttachmentItem)
    fun showAttachment(data: AttachmentItem)
}

interface SuggestionSystemCreateCategorySuggestionCallback {
    fun checkClicked(data: CategorySuggestionItem, checked: Boolean)
}