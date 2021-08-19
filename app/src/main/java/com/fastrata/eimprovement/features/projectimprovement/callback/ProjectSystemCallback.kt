package com.fastrata.eimprovement.features.projectimprovement.callback

import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementModel

interface ProjectSystemCallback {
    fun onItemClicked(data: ProjectImprovementModel)
}