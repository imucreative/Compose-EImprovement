package com.fastrata.eimprovement.features.ChangesPoint.ui

import com.fastrata.eimprovement.features.ChangesPoint.data.model.ChangePointSystemModel


interface ChangesPointCallback {
    fun onItemClicked(data: ChangePointSystemModel)
}