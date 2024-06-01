package com.tipiz.core.domain.model.payment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class DataPayment(
    val item: List<ItemPayment?>? = listOf(ItemPayment()),
    val title: String? = ""
): Parcelable

@Parcelize
data class ItemPayment(
    val image: String? = "",
    val label: String? = "",
    val status: Boolean? = false
) : Parcelable
