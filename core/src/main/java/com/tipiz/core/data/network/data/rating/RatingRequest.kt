package com.tipiz.core.data.network.data.rating

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RatingRequest(

	@field:SerializedName("review")
	var review: String? = null,

	@field:SerializedName("rating")
	var rating: Int? = null,

	@field:SerializedName("invoiceId")
	var invoiceId: String? = null
) : Parcelable
