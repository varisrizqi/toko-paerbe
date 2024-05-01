package com.tipiz.core.data.local.datasource

import com.tipiz.core.data.local.datastore.PrefDataStoreHelper
import com.tipiz.core.data.local.room.database.FavoriteDAO
import com.tipiz.core.data.local.room.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

class LocalDataStore(
    private val dataSource: PrefDataStoreHelper,
    private val roomFav: FavoriteDAO
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
    suspend fun deleteItemFav(id: String) = roomFav.deleteItemFav(id)
    fun getIsFav(id: String) = roomFav.getIsFav(id)


}