package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import com.fastrata.eimprovement.features.suggestionsystem.data.model.AttachmentItem
import com.fastrata.eimprovement.features.suggestionsystem.data.model.TeamMemberItem

interface SuggestionSystemCreateCallback {
    fun onDataPass()
}

interface SuggestionSystemCreateTeamMemberCallback {
    fun removeClicked(data: TeamMemberItem)
}

interface SuggestionSystemCreateAttachmentCallback {
    fun removeClicked(data: AttachmentItem)
    fun showAttachment(data: AttachmentItem)
}