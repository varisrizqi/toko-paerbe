package com.tipiz.core.data.network.data.fullfillmentbody

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class FulFillRequest(

    @field:SerializedName("payment")
    var payment: String? = null,

    @field:SerializedName("items")
    val items: List<ItemsItemFillFull?>? = null
) : Parcelable

@Keep
@Parcelize
data class ItemsItemFillFull(

    @field:SerializedName("quantity")
    val quantity: Int? = null,

    @field:SerializedName("productId")
    val productId: String? = null,

    @field:SerializedName("variantName")
    val variantName: String? = null
) : Parcelable
