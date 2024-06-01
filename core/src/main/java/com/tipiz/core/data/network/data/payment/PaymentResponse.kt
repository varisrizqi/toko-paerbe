package com.tipiz.core.data.network.data.payment

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentResponse(

	@field:SerializedName("code")
	val code: Int = 0,

	@field:SerializedName("data")
	val data: List<DataItem> = listOf(),

	@field:SerializedName("message")
	val message: String = ""
) : Parcelable

@Parcelize
data class PaymentItemItem(

	@field:SerializedName("image")
	val image:String = "",

	@field:SerializedName("label")
	val label: String = "",

	@field:SerializedName("status")
	val status: Boolean = false
) : Parcelable

@Parcelize
data class DataItem(

	@field:SerializedName("item")
	val item: List<PaymentItemItem?>? = listOf(),

	@field:SerializedName("title")
	val title: String = ""
) : Parcelable



