package com.tipiz.core.domain.model.transaction

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DataTransaction(
    val date:String = "",
    val image:String = "",
    val total:Int = 0,
    val review: String? = null,
    val rating: Int? = null,
    val name: String = "",
    val invoiceId: String = "",
    val payment: String = "",
    val time: String = "",
    val items: List<ItemsItemDataTransaction> = listOf(),
    val status: Boolean = false
): Parcelable

@Keep
@Parcelize
data class ItemsItemDataTransaction(
    val quantity: Int = 0,
    val productId: String = "",
    val variantName: String = ""
) : Parcelable
