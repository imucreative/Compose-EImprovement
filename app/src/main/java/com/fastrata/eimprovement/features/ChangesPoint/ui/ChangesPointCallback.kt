package com.fastrata.eimprovement.features.changesPoint.ui

import com.fastrata.eimprovement.features.changesPoint.data.model.ChangePointSystemModel


interface ChangesPointCallback {
    fun onItemClicked(data: ChangePointSystemModel)
}