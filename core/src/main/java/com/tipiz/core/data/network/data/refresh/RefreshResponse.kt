package com.tipiz.core.data.network.data.refresh

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@Keep
@Parcelize
data class RefreshResponse(

    @field:SerializedName("code")
    val code: Int = 0,

    @field:SerializedName("data")
    val data: RefreshData = RefreshData(),

    @field:SerializedName("message")
    val message: String = ""
) : Parcelable

@Parcelize
@Keep
data class RefreshData(

    @field:SerializedName("accessToken")
    val accessToken: String = "",

    @field:SerializedName("expiresAt")
    val expiresAt: Int = 0,

    @field:SerializedName("refreshToken")
    val refreshToken: String = ""
) : Parcelable
