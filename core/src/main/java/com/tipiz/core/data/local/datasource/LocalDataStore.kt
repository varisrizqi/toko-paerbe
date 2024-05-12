package com.tipiz.core.data.local.datasource

import com.tipiz.core.data.local.datastore.PrefDataStoreHelper
import com.tipiz.core.data.local.room.database.ChartDao
import com.tipiz.core.data.local.room.database.FavoriteDAO
import com.tipiz.core.data.local.room.entity.ChartEntity
import com.tipiz.core.data.local.room.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

class LocalDataStore(
    private val dataSource: PrefDataStoreHelper,
    private val roomFav: FavoriteDAO,
    private val roomChart: ChartDao
) {

    suspend fun setOnBoarding(value: Boolean) = dataSource.setOnBoarding(value)
    fun getOnBoarding(): Flow<Boolean> {
        return dataSource.getOnBoarding()
    }

    fun getAccessToken(): Flow<String> = dataSource.getAccessToken()
    suspend fun setAccessToken(value: String) {
        dataSource.setAccessToken(value)
    }

    fun getRefreshToken(): Flow<String> = dataSource.getRefreshToken()
    suspend fun setRefreshToken(value: String) {
        dataSource.setRefreshToken(value)
    }

    fun getUserName(): Flow<String> = dataSource.getUserName()
    suspend fun setUserName(value: String) {
        dataSource.setUserName(value)
    }

    suspend fun clearSession() = dataSource.clearSession()

    suspend fun setUserId(value: String) {
        dataSource.setUserid(value)
    }

    fun getUserId(): Flow<String> = dataSource.getUserid()

    suspend fun setTheme(value: Boolean) = dataSource.setTheme(value)

    fun getTheme(): Flow<Boolean> = dataSource.getTheme()

    suspend fun setLocalize(value: String) = dataSource.setLocalize(value)

    fun getLocalize(): Flow<String> = dataSource.getLocalize()

    suspend fun resetAll() {
        dataSource.resetAll()
    }

    suspend fun setIsLogin(value: Boolean) = dataSource.setIslogin(value)

    fun getIsLogin(): Flow<Boolean> = dataSource.getIsLogin()

    // ROOM

    fun getAllFav(): Flow<List<FavoriteEntity>> = roomFav.getAllFav()
    suspend fun insertFav(fav: FavoriteEntity) = roomFav.insertFav(fav)
    suspend fun deleteItemFav(id: Int) = roomFav.deleteItemFav(id)
    suspend fun deleteWishlist(fav: String) = roomFav.deleteDetailFav(fav)
    fun getIsFav(id: String) = roomFav.getIsFav(id )

    // Chart
    fun getAllChart(): Flow<List<ChartEntity>> = roomChart.getAllChart()

    suspend fun insertChart(chart: ChartEntity) = roomChart.insertChart(chart)
    fun updateCountChart(id: String, newCount: Int) =
        roomChart.updateCountChart(id, newCount)

    suspend fun updateIsCheckedChart(id: String, newIsChecked: Boolean) =
        roomChart.updateIsCheckedChart(id, newIsChecked)

    suspend fun updateCheckAllChart(value: Boolean) = roomChart.updateCheckAllChart(value)

    suspend fun deleteItemChart(id: String) = roomChart.deleteItemChart(id)
    fun getStockChart(id: String): ChartEntity? = roomChart.getStockChart(id)
    suspend fun deleteCheckedChart() = roomChart.deleteCheckedChart()


}