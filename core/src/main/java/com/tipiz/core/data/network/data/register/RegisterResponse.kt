package com.tipiz.core.data.network.data.register

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RegisterResponse(

    @field:SerializedName("code")
    val code: Int = 0,

    @field:SerializedName("data")
    val data: RegisterData = RegisterData(),

    @field:SerializedName("message")
    val message: String = ""
) : Parcelable

@Parcelize
@Keep
data class RegisterData(

    @field:SerializedName("accessToken")
    val accessToken: String = "",

    @field:SerializedName("expiresAt")
    val expiresAt: Int = 0,

    @field:SerializedName("refreshToken")
    val refreshToken: String = ""
) : Parcelable
