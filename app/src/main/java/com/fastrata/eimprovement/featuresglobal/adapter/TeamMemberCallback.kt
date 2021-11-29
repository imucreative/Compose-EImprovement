package com.fastrata.eimprovement.featuresglobal.adapter

import com.fastrata.eimprovement.featuresglobal.data.model.TeamMemberItem

interface TeamMemberCallback {
    fun removeClicked(data: TeamMemberItem)
}