package com.tipiz.core.data.local.room.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tipiz.core.data.local.room.entity.FavoriteEntity
import com.tipiz.core.data.local.room.entity.PagingKeys
import com.tipiz.core.data.local.room.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {


    // ===== products =====
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: List<ProductEntity>)

    @Query("SELECT * FROM product_table")
    fun retrieveAllProducts(): PagingSource<Int, ProductEntity>

    @Query("DELETE FROM product_table")
    suspend fun deleteAll()

    // ===== paging =====
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pagingKey: List<PagingKeys>)

    @Query("SELECT * FROM paging_key WHERE id = :id")
    suspend fun getPagingKeysId(id: String): PagingKeys?

    @Query("DELETE FROM paging_key")
    suspend fun deleteAllKey()

    // ===== fav =====

    @Query("SELECT * FROM favorite_table")
    fun getAllFav(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFav(fav: FavoriteEntity)

    @Query("DELETE FROM favorite_table WHERE productId = :id")
    suspend fun deleteItemFav(id: String)

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_table WHERE productId = :id)")
    fun getIsFav(id: String): Flow<Boolean>
}