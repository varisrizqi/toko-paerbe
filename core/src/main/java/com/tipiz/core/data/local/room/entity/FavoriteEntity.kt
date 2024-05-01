package com.tipiz.core.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tipiz.core.utils.Constant

@Entity(tableName = Constant.favorite_table)
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = "productId")
    val productId: String = "",
    @ColumnInfo(name = "brand")
    val brand: String = "",
    @ColumnInfo(name = "description")
    val description: String = "",
    @ColumnInfo(name = "image")
    val image:String = "",
    @ColumnInfo(name = "productName")
    val productName: String = "",
    @ColumnInfo(name = "productPrice")
    val productPrice: Int = 0,
    @ColumnInfo(name = "productRating")
    val productRating: Double = 0.0,
    @ColumnInfo(name = "variantName")
    val variantName: String = "",
    @ColumnInfo(name = "variantPrice")
    val variantPrice: Int = 0,
    @ColumnInfo(name = "sale")
    val sale: Int = 0,
    @ColumnInfo(name = "stock")
    val stock: Int = 0,
    @ColumnInfo(name = "store")
    val store: String = "",
    @ColumnInfo(name = "totalRating")
    val totalRating: Int = 0,
    @ColumnInfo(name = "totalReview")
    val totalReview: Int = 0,
    @ColumnInfo(name = "totalSatisfaction")
    val totalSatisfaction: Int = 0,
    @ColumnInfo(name = "setChip")
    val setChip: Int = 0
)