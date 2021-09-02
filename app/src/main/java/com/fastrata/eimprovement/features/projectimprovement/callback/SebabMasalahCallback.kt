package com.fastrata.eimprovement.features.projectimprovement.callback

import com.fastrata.eimprovement.features.projectimprovement.data.model.SebabMasalahItem

interface SebabMasalahCallback {
    fun onItemClicked(data: SebabMasalahItem )
}

