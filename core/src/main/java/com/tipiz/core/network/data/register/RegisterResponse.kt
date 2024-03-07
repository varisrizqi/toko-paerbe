package com.tipiz.core.network.data.register

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("accessToken")
	val accessToken: String,

	@field:SerializedName("expiresAt")
	val expiresAt: Int,

	@field:SerializedName("refreshToken")
	val refreshToken: String
) : Parcelable
