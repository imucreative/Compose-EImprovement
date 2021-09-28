package com.fastrata.eimprovement.ui.adapter

import com.fastrata.eimprovement.ui.model.CategoryImprovementItem

interface CategoryImprovementCallback {
    fun checkClicked(data: CategoryImprovementItem, checked: Boolean)
}