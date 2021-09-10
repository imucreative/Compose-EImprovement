package com.fastrata.eimprovement.features.changespoint.ui

import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointModel

interface ChangesPointCallback {
    fun onItemClicked(data: ChangePointModel)
}

interface ChangesPointCreateCallback {
    fun OnDataPass() : Boolean
}

