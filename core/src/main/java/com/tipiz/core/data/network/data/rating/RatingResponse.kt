package com.tipiz.core.data.network.data.rating

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RatingResponse(

	@field:SerializedName("code")
	val code: Int = 0,

	@field:SerializedName("message")
	val message: String = ""
) : Parcelable
