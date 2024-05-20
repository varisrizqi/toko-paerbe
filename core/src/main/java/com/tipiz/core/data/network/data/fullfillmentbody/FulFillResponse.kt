package com.tipiz.core.data.network.data.fullfillmentbody

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FulFillResponse(

	@field:SerializedName("code")
	val code: Int = 0,

	@field:SerializedName("data")
	val data: DataFillFull = DataFillFull(),

	@field:SerializedName("message")
	val message: String = ""
) : Parcelable

@Parcelize
data class DataFillFull(

	@field:SerializedName("date")
	val date: String = "",

	@field:SerializedName("total")
	val total: Int = 0,

	@field:SerializedName("invoiceId")
	val invoiceId: String = "",

	@field:SerializedName("payment")
	val payment: String = "",

	@field:SerializedName("time")
	val time: String = "",

	@field:SerializedName("status")
	val status: Boolean = false
) : Parcelable
