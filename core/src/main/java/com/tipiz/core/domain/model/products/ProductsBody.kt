package com.tipiz.core.domain.model.products

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ProductsBody(
    var search: String?,
    var brand: String?,
    var lowest: Int?,
    var highest: Int?,
    var sort: String?,
    var limit: Int?,
    var page: Int?
): Parcelable
