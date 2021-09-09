package com.fastrata.eimprovement.features.changesPoint.ui

import com.fastrata.eimprovement.features.changesPoint.data.model.ChangePointModel

interface ChangesPointCallback {
    fun onItemClicked(data: ChangePointModel)
}

interface ChangesPointCreateCallback {
    fun OnDataPass() : Boolean
}

