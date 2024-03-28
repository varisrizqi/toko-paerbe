package com.tipiz.core.data.network.data.products

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProductsResponse(

    @field:SerializedName("code")
    val code: Int = 0,

    @field:SerializedName("data")
    val data: ProductData = ProductData(),

    @field:SerializedName("message")
    val message: String = ""
) : Parcelable

@Parcelize
data class ItemsItem(

    @field:SerializedName("image")
    val image: String = "",

    @field:SerializedName("sale")
    val sale: Int = 0,

    @field:SerializedName("productId")
    val productId: String = "",

    @field:SerializedName("store")
    val store: String = "",

    @field:SerializedName("productRating")
    val productRating: Float = 0.0f,

    @field:SerializedName("brand")
    val brand: String = "",

    @field:SerializedName("productName")
    val productName: String = "",

    @field:SerializedName("productPrice")
    val productPrice: Int = 0
) : Parcelable

@Parcelize
data class ProductData(

    @field:SerializedName("pageIndex")
    val pageIndex: Int = 0,

    @field:SerializedName("itemsPerPage")
    val itemsPerPage: Int = 0,

    @field:SerializedName("currentItemCount")
    val currentItemCount: Int = 0,

    @field:SerializedName("totalPages")
    val totalPages: Int = 0,

    @field:SerializedName("items")
    val items: List<ItemsItem> = listOf()
) : Parcelable

