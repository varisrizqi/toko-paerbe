package com.tipiz.core.data.network.data.login

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class LoginResponse(

    @field:SerializedName("code")
    val code: Int = 0,

    @field:SerializedName("data")
    val data: LoginData = LoginData(),

    @field:SerializedName("message")
    val message: String = ""
) : Parcelable

@Keep
@Parcelize
data class LoginData(

    @field:SerializedName("userImage")
    val userImage: String = "",

    @field:SerializedName("userName")
    val userName: String = "",

    @field:SerializedName("accessToken")
    val accessToken: String = "",

    @field:SerializedName("expiresAt")
    val expiresAt: Int = 0,

    @field:SerializedName("refreshToken")
    val refreshToken: String = ""
) : Parcelable

