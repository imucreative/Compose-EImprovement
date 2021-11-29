package com.fastrata.eimprovement.featuresglobal.adapter

import com.fastrata.eimprovement.featuresglobal.data.model.CategoryImprovementItem

interface CategoryImprovementCallback {
    fun checkClicked(data: CategoryImprovementItem, checked: Boolean)
}