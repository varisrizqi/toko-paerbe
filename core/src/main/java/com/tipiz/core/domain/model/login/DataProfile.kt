package com.tipiz.core.domain.model.login

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DataProfile(
    var userName: String = "",
    var userImage: String = ""
) : Parcelable
