package com.tipiz.core.data.network.data.login

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class LoginRequest(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("firebaseToken")
	val firebaseToken: String? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable
