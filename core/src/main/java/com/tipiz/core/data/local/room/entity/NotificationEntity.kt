package com.tipiz.core.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification")
data class NotificationEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "body")
    val body: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "time")
    val time: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "isChecked")
    var isChecked: Boolean = false


)