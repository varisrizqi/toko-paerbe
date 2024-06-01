package com.tipiz.core.domain.model.firebase

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Notification(
    val id: Int,
    val body: String,
    val date: String,
    val image: String,
    val time: String,
    val title: String,
    val type: String,
    var isChecked: Boolean = false
) : Parcelable
