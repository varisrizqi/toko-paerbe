package com.tipiz.core.data.network.data.refresh

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RefreshRequest(

	@field:SerializedName("token")
	val token: String?
) : Parcelable
