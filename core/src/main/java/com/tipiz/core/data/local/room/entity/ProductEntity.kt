package com.tipiz.core.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tipiz.core.utils.Constant


@Entity(tableName = Constant.product_table)
data class ProductEntity(
    @PrimaryKey
    @ColumnInfo(name = "productId")
    val productId: String = "",
    @ColumnInfo(name = "image")
    val image: String = "",
    @ColumnInfo(name = "productName")
    val productName: String = "",
    @ColumnInfo(name = "productPrice")
    val productPrice: Int = 0,
    @ColumnInfo(name = "productRating")
    val productRating: Float = 0.0f,
    @ColumnInfo(name = "sale")
    val  sale: Int = 0,
    @ColumnInfo(name = "store")
    val store: String = "",


)

