package com.fastrata.eimprovement.ui.adapter

import com.fastrata.eimprovement.ui.model.TeamMemberItem

interface TeamMemberCallback {
    fun removeClicked(data: TeamMemberItem)
}