package com.fastrata.eimprovement.features.projectimprovement.callback

import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementItem

interface ProjectSystemCallback {
    fun onItemClicked(data: ProjectImprovementItem)
}