package com.tipiz.core.data.local.room.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tipiz.core.data.local.room.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Query("select * from notification")
    fun getAllNotification(): Flow<List<NotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Query("update notification set isChecked= :newIsChecked where id = :id")
    suspend fun updateIsCheckedNotification(id: Int, newIsChecked: Boolean)
}