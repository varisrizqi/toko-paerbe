package com.tipiz.core.data.network.data.search

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: List<String>,

	@field:SerializedName("message")
	val message: String
) : Parcelable
