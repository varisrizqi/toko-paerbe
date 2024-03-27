package com.tipiz.core.remote.data.detail

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DetailResponse(

	@field:SerializedName("code")
	val code: Int = 0,

	@field:SerializedName("data")
	val data: DataDetail = DataDetail(),

	@field:SerializedName("message")
	val message: String = ""
) : Parcelable

@Parcelize
data class ProductVariantItem(

	@field:SerializedName("variantPrice")
	val variantPrice: Int= 0,

	@field:SerializedName("variantName")
	val variantName: String = ""
) : Parcelable

@Parcelize
data class DataDetail(

	@field:SerializedName("image")
	val image: List<String> = listOf(),

	@field:SerializedName("productId")
	val productId: String= "",

	@field:SerializedName("description")
	val description: String= "",

	@field:SerializedName("totalRating")
	val totalRating: Int = 0,

	@field:SerializedName("store")
	val store: String= "",

	@field:SerializedName("productName")
	val productName: String= "",

	@field:SerializedName("totalSatisfaction")
	val totalSatisfaction: Int = 0,

	@field:SerializedName("sale")
	val sale: Int = 0,

	@field:SerializedName("productVariant")
	val productVariant: List<ProductVariantItem> = listOf(),

	@field:SerializedName("stock")
	val stock: Int = 0,

	@field:SerializedName("productRating")
	val productRating: Float= 0.0f,

	@field:SerializedName("brand")
	val brand: String = "",

	@field:SerializedName("productPrice")
	val productPrice:  Int= 0,

	@field:SerializedName("totalReview")
	val totalReview:  Int= 0,
) : Parcelable
