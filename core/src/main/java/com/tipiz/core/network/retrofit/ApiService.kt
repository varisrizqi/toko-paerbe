package com.tipiz.core.network.retrofit

import com.tipiz.core.network.data.login.LoginRequest
import com.tipiz.core.network.data.login.LoginResponse
import com.tipiz.core.network.data.refresh.RefreshRequest
import com.tipiz.core.network.data.refresh.RefreshResponse
import com.tipiz.core.network.data.register.RegisterRequest
import com.tipiz.core.network.data.register.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    suspend fun fetchRegister(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("login")
    suspend fun fetchLogin(
        @Body request:LoginRequest
    ):LoginResponse

    @POST("refresh")
    suspend fun fetchRefreshToken(
        @Body request:RefreshRequest
    ):RefreshResponse



}