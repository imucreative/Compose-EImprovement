package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import com.fastrata.eimprovement.features.suggestionsystem.data.model.TeamMemberItem

interface SuggestionSystemCreateCallback {
    fun onDataPass()
}

interface SuggestionSystemCreateTeamMemberCallback {
    fun removeClicked(data: TeamMemberItem)
}