package com.tipiz.core.domain.repository

import com.tipiz.core.domain.model.login.DataSession
import com.tipiz.core.network.data.login.LoginRequest
import com.tipiz.core.network.data.login.LoginResponse
import com.tipiz.core.network.data.refresh.RefreshRequest
import com.tipiz.core.network.data.refresh.RefreshResponse
import com.tipiz.core.network.data.register.RegisterRequest
import com.tipiz.core.network.data.register.RegisterResponse
import kotlinx.coroutines.flow.Flow

interface TokoRepository {
    //Local DataStore
    suspend fun setOnBoarding(value:Boolean)
    fun getOnBoarding(): Flow<Boolean>
    suspend fun setAccessToken(value: String)
    fun getAccessToken(): Flow<String>
    suspend fun setRefreshToken(value: String)
    fun getRefreshToken(): Flow<String>
    suspend fun setUserName(value: String)
    fun getUserName(): Flow<String>
    fun clearSession()
    fun getSessionData(): Flow<DataSession>
    suspend fun setUserId(value: String)
    fun getUserId():Flow<String>

    //Remote Api
    suspend fun fetchRegister(request: RegisterRequest): RegisterResponse
    suspend fun fetchLogin(request: LoginRequest): LoginResponse
    suspend fun fetchRefreshToken(request: RefreshRequest): RefreshResponse


}