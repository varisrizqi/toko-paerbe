package com.tipiz.core.domain.usecase

import com.tipiz.core.domain.model.login.DataLogin
import com.tipiz.core.domain.model.login.DataSession
import com.tipiz.core.domain.model.login.DataToken
import com.tipiz.core.network.data.login.LoginRequest
import com.tipiz.core.network.data.refresh.RefreshRequest
import com.tipiz.core.network.data.register.RegisterRequest
import kotlinx.coroutines.flow.Flow

interface TokoUseCase {
    //Local DataStore
    suspend fun setOnBoarding(value: Boolean)
    fun getOnBoarding(): Flow<Boolean>

    suspend fun setAccessToken(value: String)

    fun getAccessToken(): Flow<String>

    suspend fun setRefreshToken(value: String)

    fun getRefreshToken(): Flow<String>

    suspend fun setUserName(value: String)

    fun getUserName(): Flow<String>

    fun getSessionData(): Flow<DataSession>

    suspend fun setUserId(value: String)
    fun getUserId():Flow<String>

    //Remote Api
    suspend fun fetchRegister(request: RegisterRequest): DataToken
    suspend fun fetchLogin(request: LoginRequest): DataLogin
    suspend fun fetchRefreshToken(request: RefreshRequest): DataToken
}