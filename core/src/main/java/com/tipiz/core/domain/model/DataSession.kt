package com.tipiz.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataSession (
    var name: String = "",
    var accessToken : String = "",
    var onBoardingState : Boolean = false,
) : Parcelable
