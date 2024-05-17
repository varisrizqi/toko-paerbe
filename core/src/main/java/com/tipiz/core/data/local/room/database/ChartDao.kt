package com.tipiz.core.data.local.room.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tipiz.core.data.local.room.entity.ChartEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ChartDao {

    @Query("SELECT * FROM chart_table")
    fun getAllChart(): Flow<List<ChartEntity>> // 2 menampilkan data

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChart(chart: ChartEntity) // 1.1

    @Query("UPDATE chart_table SET amount = :newCount WHERE productId = :id")
    fun updateCountChart(id: String, newCount: Int) // 1.2 update count jumlah

    @Query("UPDATE chart_table SET isChecked = :newIsChecked WHERE productId = :id")
    suspend fun updateIsCheckedChart(id: String, newIsChecked: Boolean) // check box per item

    @Query("UPDATE chart_table SET isChecked = :value")
    suspend fun updateCheckAllChart(value: Boolean) //  check box all

    @Query("DELETE FROM chart_table WHERE productId = :id")
    suspend fun deleteItemChart(id: String) //  delete keranjang sampah

    @Query("SELECT * FROM chart_table WHERE productId = :id")
    fun getStockChart(id: String): ChartEntity? // 1.3 menampilkan data berdasarkan id

    @Query("DELETE FROM chart_table WHERE isChecked = 1")
    suspend fun deleteCheckedChart() //  delete all
}