package com.tipiz.core.data.network.datasource

import com.tipiz.core.data.network.data.login.LoginRequest
import com.tipiz.core.data.network.data.login.LoginResponse
import com.tipiz.core.data.network.data.profile.ProfileResponse
import com.tipiz.core.data.network.data.refresh.RefreshRequest
import com.tipiz.core.data.network.data.refresh.RefreshResponse
import com.tipiz.core.data.network.data.register.RegisterRequest
import com.tipiz.core.data.network.data.register.RegisterResponse
import com.tipiz.core.data.network.retrofit.ApiService
import com.tipiz.core.utils.state.safeApiCall
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RemoteDataSource(private val apiEndpoint: ApiService) {

    suspend fun fetchRegister(request: RegisterRequest): RegisterResponse {
        return apiEndpoint.fetchRegister(request = request)
    }

    suspend fun fetchLogin(request: LoginRequest): LoginResponse =
        safeApiCall { apiEndpoint.fetchLogin(request = request) }

    suspend fun fetchRefreshToken(request: RefreshRequest): RefreshResponse =
        safeApiCall { apiEndpoint.fetchRefreshToken(request = request) }

    suspend fun fetchProfile(
        userName: RequestBody,
        userImage: MultipartBody.Part
    ): ProfileResponse {
        return safeApiCall { apiEndpoint.fetchProfile(userName = userName, userImage = userImage) }
    }


}