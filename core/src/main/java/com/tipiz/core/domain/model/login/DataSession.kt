package com.tipiz.core.domain.model.login

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DataSession (
    var userName: String = "",
    var accessToken : String = "",
    var onBoardingState : Boolean = false,
) : Parcelable
