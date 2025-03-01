package com.tipiz.core.data.network.data.transaction

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionResponse(

	@field:SerializedName("code")
	val code: Int = 0,

	@field:SerializedName("data")
	val data: List<DataItemTransaction> = listOf(),

	@field:SerializedName("message")
	val message: String = ""
) : Parcelable

@Parcelize
data class DataItemTransaction(

	@field:SerializedName("date")
	val date: String = "",

	@field:SerializedName("image")
	val image: String = "",

	@field:SerializedName("total")
	val total: Int = 0,

	@field:SerializedName("review")
	val review: String? = null,

	@field:SerializedName("rating")
	val rating: Int? = null,

	@field:SerializedName("name")
	val name: String = "",

	@field:SerializedName("invoiceId")
	val invoiceId: String = "",

	@field:SerializedName("payment")
	val payment: String = "",

	@field:SerializedName("time")
	val time: String = "",

	@field:SerializedName("items")
	val items: List<ItemsItemTransaction> = listOf(),

	@field:SerializedName("status")
	val status: Boolean = false
) : Parcelable

@Parcelize
data class ItemsItemTransaction(

	@field:SerializedName("quantity")
	val quantity: Int = 0,

	@field:SerializedName("productId")
	val productId: String = "",

	@field:SerializedName("variantName")
	val variantName: String = ""
) : Parcelable
