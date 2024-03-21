package com.tipiz.core.local.pref

import com.tipiz.core.domain.model.login.DataSession
import kotlinx.coroutines.flow.Flow

interface PrefDataStoreHelper {
    suspend fun setOnBoarding(value: Boolean)
    fun getOnBoarding(): Flow<Boolean>

    suspend fun setAccessToken(value: String)
    fun getAccessToken(): Flow<String>

    suspend fun setRefreshToken(value: String)
    fun getRefreshToken(): Flow<String>

    suspend fun setUserName(value: String)
    fun getUserName(): Flow<String>

    fun clearSession()

    fun getSessionData(): Flow<DataSession>

    suspend fun setUserid(value: String)
    fun getUserid():Flow<String>
}