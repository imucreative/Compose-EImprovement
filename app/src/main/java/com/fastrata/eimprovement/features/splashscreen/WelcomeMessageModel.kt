package com.fastrata.eimprovement.features.splashscreen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WelcomeMessageModel (
    var isDisplay: Boolean = false
) : Parcelable
