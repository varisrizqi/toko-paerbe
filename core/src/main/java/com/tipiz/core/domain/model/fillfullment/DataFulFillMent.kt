package com.tipiz.core.domain.model.fillfullment

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DataFulFillMent(
    val date: String = "",
    val total: Int = 0,
    val invoiceId: String = "",
    val payment: String = "",
    val time: String = "",
    val status: Boolean = false


): Parcelable
