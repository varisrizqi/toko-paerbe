package com.tipiz.core.data.network.data.profile

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@Keep
@Parcelize
data class ProfileResponse(

    @field:SerializedName("code")
	val code: Int = 0,

    @field:SerializedName("data")
	val data: ProfileData = ProfileData(),

    @field:SerializedName("message")
	val message: String =""
) : Parcelable
@Keep
@Parcelize
data class ProfileData(

	@field:SerializedName("userImage")
	val userImage: String="",

	@field:SerializedName("userName")
	val userName: String=""
) : Parcelable
