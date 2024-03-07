package com.tipiz.core.network.data.login

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class LoginResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("userImage")
	val userImage: String,

	@field:SerializedName("userName")
	val userName: String,

	@field:SerializedName("accessToken")
	val accessToken: String,

	@field:SerializedName("expiresAt")
	val expiresAt: Int,

	@field:SerializedName("refreshToken")
	val refreshToken: String
) : Parcelable
