package com.tipiz.core.data.local.room.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tipiz.core.data.local.room.entity.PagingKeys
import com.tipiz.core.data.local.room.entity.ProductEntity

@Dao
interface Dao {


    // ===== products paging =====
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: List<ProductEntity>)

    @Query("SELECT * FROM product_table")
    fun retrieveAllProducts(): PagingSource<Int, ProductEntity>

    @Query("DELETE FROM product_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pagingKey: List<PagingKeys>)

    @Query("SELECT * FROM paging_key WHERE id = :id")
    suspend fun getPagingKeysId(id: String): PagingKeys?

    @Query("DELETE FROM paging_key")
    suspend fun deleteAllKey()


}