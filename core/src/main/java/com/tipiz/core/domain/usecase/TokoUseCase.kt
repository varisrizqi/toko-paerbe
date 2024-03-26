package com.tipiz.core.domain.usecase

import com.tipiz.core.domain.model.login.DataLogin
import com.tipiz.core.domain.model.login.DataProfile
import com.tipiz.core.domain.model.login.DataToken
import com.tipiz.core.data.network.data.login.LoginRequest
import com.tipiz.core.data.network.data.refresh.RefreshRequest
import com.tipiz.core.data.network.data.register.RegisterRequest
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

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

    suspend fun setUserId(value: String)
    fun getUserId():Flow<String>

    suspend fun clearSession()

    //Remote Api
    suspend fun fetchRegister(request: RegisterRequest): DataToken
    suspend fun fetchLogin(request: LoginRequest): DataLogin
    suspend fun fetchRefreshToken(request: RefreshRequest): DataToken

    suspend fun fetchProfile(
        userName: RequestBody,
        userImage: MultipartBody.Part
    ): DataProfile
}