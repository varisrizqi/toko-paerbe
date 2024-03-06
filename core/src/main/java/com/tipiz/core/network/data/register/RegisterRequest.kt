package com.tipiz.core.network.data.register

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterRequest(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("firebaseToken")
	val firebaseToken: String? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable
