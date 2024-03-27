package com.tipiz.core.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tipiz.core.utils.Constant

@Entity(tableName = Constant.paging_key)
data class PagingKeys(
    @PrimaryKey
    @ColumnInfo("id")
    val id: String,
    @ColumnInfo("prevKey")
    val prevKey: Int?,
    @ColumnInfo("nextKey")
    val nextKey: Int?
)

