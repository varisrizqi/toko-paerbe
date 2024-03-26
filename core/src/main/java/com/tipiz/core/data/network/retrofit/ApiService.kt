package com.tipiz.core.data.network.retrofit

import com.tipiz.core.data.network.data.login.LoginRequest
import com.tipiz.core.data.network.data.login.LoginResponse
import com.tipiz.core.data.network.data.profile.ProfileResponse
import com.tipiz.core.data.network.data.refresh.RefreshRequest
import com.tipiz.core.data.network.data.refresh.RefreshResponse
import com.tipiz.core.data.network.data.register.RegisterRequest
import com.tipiz.core.data.network.data.register.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST("register")
    suspend fun fetchRegister(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("login")
    suspend fun fetchLogin(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("refresh")
    suspend fun fetchRefreshToken(
        @Body request: RefreshRequest
    ): RefreshResponse

    @POST("profile")
    @Multipart
    suspend fun fetchProfile(
        @Part("userName") userName: RequestBody,
        @Part userImage: MultipartBody.Part
    ): ProfileResponse


}