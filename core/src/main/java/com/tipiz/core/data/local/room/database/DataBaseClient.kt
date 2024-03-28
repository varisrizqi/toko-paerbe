package com.tipiz.core.data.local.room.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.tipiz.core.data.local.room.entity.FavoriteEntity
import com.tipiz.core.data.local.room.entity.PagingKeys
import com.tipiz.core.data.local.room.entity.ProductEntity

@Database(
    entities = [PagingKeys::class, ProductEntity::class, FavoriteEntity::class],
    version = 1,
)

abstract class DataBaseClient : RoomDatabase() {
    abstract fun appDao(): Dao

}