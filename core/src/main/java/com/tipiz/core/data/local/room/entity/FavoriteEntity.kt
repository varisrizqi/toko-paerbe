package com.tipiz.core.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tipiz.core.utils.Constant

@Entity(tableName = Constant.favorite_table)
data class FavoriteEntity(
    @PrimaryKey
    val productId: String,
    val brand: String,
    val description: String,
    val image: String,
    val productName: String,
    val productPrice: Int,
    val productRating: Double,
    val variantName: String,
    val variantPrice: Int,
    val sale: Int,
    val stock: Int,
    val store: String,
    val totalRating: Int,
    val totalReview: Int,
    val totalSatisfaction: Int,
)