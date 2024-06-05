package com.tipiz.core.domain.model.rating

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DataRating(
    val code: Int = 0,
    val message: String = ""
): Parcelable
