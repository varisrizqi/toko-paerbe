package com.tipiz.core.domain.model.cart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataCart(
    val productId: String = "",
    val brand: String = "",
    val description: String = "",
    val image: String = "",
    val productName: String = "",
    val productPrice: Int = 0,
    val productRating: Double = 0.0,
    var variantName: String = "",
    var variantPrice: Int = 0,
    val sale: Int= 0,
    val stock: Int= 0,
    val store: String = "",
    val totalRating: Int= 0,
    val totalReview: Int= 0,
    val totalSatisfaction: Int= 0,
    var isChecked: Boolean = false,
    var amount: Int = 0
) : Parcelable
