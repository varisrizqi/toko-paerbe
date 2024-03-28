package com.tipiz.core.domain.repository

import androidx.paging.PagingData
import com.tipiz.core.data.local.room.entity.ProductEntity
import com.tipiz.core.data.network.data.login.LoginRequest
import com.tipiz.core.data.network.data.login.LoginResponse
import com.tipiz.core.data.network.data.profile.ProfileResponse
import com.tipiz.core.data.network.data.refresh.RefreshRequest
import com.tipiz.core.data.network.data.refresh.RefreshResponse
import com.tipiz.core.data.network.data.register.RegisterRequest
import com.tipiz.core.data.network.data.register.RegisterResponse
import com.tipiz.core.remote.data.detail.DetailResponse
import com.tipiz.core.remote.data.review.ReviewResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface TokoRepository {
    //Local DataStore
    suspend fun setOnBoarding(value: Boolean)
    fun getOnBoarding(): Flow<Boolean>
    suspend fun setAccessToken(value: String)
    fun getAccessToken(): Flow<String>
    suspend fun setRefreshToken(value: String)
    fun getRefreshToken(): Flow<String>
    suspend fun setUserName(value: String)
    fun getUserName(): Flow<String>
    suspend fun clearSession()

    suspend fun setUserId(value: String)
    fun getUserId(): Flow<String>

    suspend fun setTheme(value:Boolean)

    fun getTheme ():Flow<Boolean>

    suspend fun setLocalize(value:String)

    fun getLocalize():Flow<String>

    suspend fun resetAll()

    //Remote Api
    suspend fun fetchRegister(request: RegisterRequest): RegisterResponse
    suspend fun fetchLogin(request: LoginRequest): LoginResponse
    suspend fun fetchRefreshToken(request: RefreshRequest): RefreshResponse

    suspend fun fetchProfile(
        userName: RequestBody,
        userImage: MultipartBody.Part
    ): ProfileResponse

    suspend fun fetchProductLocal(): Flow<PagingData<ProductEntity>>

    suspend fun fetchDetailProduct(
        id: String
    ): DetailResponse

    suspend fun fetchReviewProduct(
        id: String
    ): ReviewResponse
}