package com.tipiz.core.domain.model.products

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DataProduct(
    val productId: String = "",
    val productName: String = "",
    val productPrice: Int = 0,
    val image: String = "",
    val store: String = "",
    val sale: Int = 0,
    val brand: String = "",
    val productRating: Float = 0.0f
): Parcelable
