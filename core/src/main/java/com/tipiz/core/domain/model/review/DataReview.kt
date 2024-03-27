package com.tipiz.core.domain.model.review

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataReview(
    var userName: String = "",
    var userImage: String = "",
    var userRating: Int = 0,
    var userReview: String = ""
) : Parcelable
