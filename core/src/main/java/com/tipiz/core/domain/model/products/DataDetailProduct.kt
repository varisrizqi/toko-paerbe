package com.tipiz.core.domain.model.products

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProductVariant(
    var variantName: String = "",
    var variantPrice: Int = 0
) : Parcelable


@Parcelize
data class DataDetailProduct(
    var productId: String = "",
    var productName: String = "",
    var productPrice: Int = 0,
//    var image: String = "",
    var image: List<String> = listOf(),
    var brand: String = "",
    var description: String = "",
    var store: String = "",
    var sale: Int = 0,
    var stock: Int = 0,
    var totalRating: Int = 0,
    var totalSatisfaction: Int = 0,
    var productRating: Double = 0.0,
//    var variantName: String = "",
//    var variantPrice: Int = 0,
    var productVariant: List<ProductVariant> = listOf(),
    var totalReview:  Int = 0,
    var isChecked: Boolean = false,
    var count: Int = 0,
    var chip: Int = 0

) : Parcelable
