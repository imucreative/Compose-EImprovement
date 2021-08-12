package com.fastrata.eimprovement.features.projectimprovement.ui

import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementModel

interface ProjectSystemCallback {
    fun onItemClicked(data: ProjectImprovementModel)
}