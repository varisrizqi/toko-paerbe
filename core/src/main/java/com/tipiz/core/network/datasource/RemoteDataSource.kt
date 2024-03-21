package com.tipiz.core.network.datasource

import com.tipiz.core.network.data.login.LoginRequest
import com.tipiz.core.network.data.login.LoginResponse
import com.tipiz.core.network.data.refresh.RefreshRequest
import com.tipiz.core.network.data.refresh.RefreshResponse
import com.tipiz.core.network.data.register.RegisterRequest
import com.tipiz.core.network.data.register.RegisterResponse
import com.tipiz.core.network.retrofit.ApiService
import com.tipiz.core.utils.state.safeApiCall

class RemoteDataSource(private val apiEndpoint: ApiService) {

    suspend fun fetchRegister(request: RegisterRequest): RegisterResponse {
        return apiEndpoint.fetchRegister(request = request)
    }

    suspend fun fetchLogin(request: LoginRequest): LoginResponse = safeApiCall { apiEndpoint.fetchLogin(request = request) }
    suspend fun fetchRefreshToken(request: RefreshRequest): RefreshResponse =
        safeApiCall { apiEndpoint.fetchRefreshToken(request = request) }


}