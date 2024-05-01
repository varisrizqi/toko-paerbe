package com.tipiz.core.data.local.room.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tipiz.core.data.local.room.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDAO {

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