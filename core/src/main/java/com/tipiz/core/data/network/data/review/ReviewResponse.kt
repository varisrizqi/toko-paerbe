package com.tipiz.core.remote.data.review

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ReviewResponse(

	@field:SerializedName("code")
	val code: Int = 0,

	@field:SerializedName("data")
	val data: List<DataItemReview> = listOf(),

	@field:SerializedName("message")
	val message: String
) : Parcelable

@Parcelize
data class DataItemReview(

	@field:SerializedName("userImage")
	val userImage: String = "",

	@field:SerializedName("userName")
	val userName: String= "",

	@field:SerializedName("userReview")
	val userReview: String= "",

	@field:SerializedName("userRating")
	val userRating: Int = 0
) : Parcelable
