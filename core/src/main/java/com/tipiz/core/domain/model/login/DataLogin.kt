package com.tipiz.core.domain.model.login

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DataLogin(
    val userImage: String = "",
    val userName: String = "",
    val accessToken: String = "",
    val expiresAt: Int = 0,
    val refreshToken: String = ""
) : Parcelable
