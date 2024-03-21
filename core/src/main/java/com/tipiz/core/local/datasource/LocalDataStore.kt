package com.tipiz.core.local.datasource

import com.tipiz.core.domain.model.login.DataSession
import com.tipiz.core.local.pref.PrefDataStoreHelper
import kotlinx.coroutines.flow.Flow

class LocalDataStore(private val dataSource: PrefDataStoreHelper) {

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

    fun clearSession() = dataSource.clearSession()

    fun getSessionData(): Flow<DataSession> = dataSource.getSessionData()

    suspend fun setUserId(value: String){
        dataSource.setUserid(value)
    }
    fun getUserId():Flow<String> = dataSource.getUserid()

}