package com.tipiz.core.domain.repository

import androidx.paging.PagingData
import com.tipiz.core.data.local.datasource.LocalDataStore
import com.tipiz.core.data.local.datasource.PagingDataSource
import com.tipiz.core.data.local.room.entity.ProductEntity
import com.tipiz.core.data.network.data.login.LoginRequest
import com.tipiz.core.data.network.data.login.LoginResponse
import com.tipiz.core.data.network.data.profile.ProfileResponse
import com.tipiz.core.data.network.data.refresh.RefreshRequest
import com.tipiz.core.data.network.data.refresh.RefreshResponse
import com.tipiz.core.data.network.data.register.RegisterRequest
import com.tipiz.core.data.network.data.register.RegisterResponse
import com.tipiz.core.data.network.datasource.RemoteDataSource
import com.tipiz.core.remote.data.detail.DetailResponse
import com.tipiz.core.remote.data.review.ReviewResponse
import com.tipiz.core.utils.state.safeDataCall
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class TokoRepositoryImpl(
    private val local: LocalDataStore,
    private val remote: RemoteDataSource,
    private val paging: PagingDataSource
) : TokoRepository {

    //Local DataStore
    override suspend fun setOnBoarding(value: Boolean) {
        local.setOnBoarding(value)
    }

    override fun getOnBoarding(): Flow<Boolean> = local.getOnBoarding()


    override suspend fun setAccessToken(value: String) {
        local.setAccessToken(value)
    }

    override fun getAccessToken(): Flow<String> = local.getAccessToken()

    override suspend fun setRefreshToken(value: String) {
        local.setRefreshToken(value)
    }

    override fun getRefreshToken(): Flow<String> = local.getRefreshToken()

    override suspend fun setUserName(value: String) {
        local.setUserName(value)
    }

    override fun getUserName(): Flow<String> = local.getUserName()

    override suspend fun clearSession() {
        local.clearSession()
    }

    override suspend fun setUserId(value: String) {
        local.setUserId(value)
    }

    override fun getUserId(): Flow<String> = local.getUserId()

    override suspend fun setTheme(value: Boolean) {
       local.setTheme(value)
    }

    override fun getTheme(): Flow<Boolean> = local.getTheme()
    override suspend fun setLocalize(value: String) {
        local.setLocalize(value)
    }

    override fun getLocalize(): Flow<String> = local.getLocalize()
    override suspend fun resetAll() {
        local.resetAll()
    }

    //Remote Api
    override suspend fun fetchRegister(request: RegisterRequest): RegisterResponse = safeDataCall {
        remote.fetchRegister(request = request)
    }

    override suspend fun fetchLogin(request: LoginRequest): LoginResponse = safeDataCall {
        remote.fetchLogin(request = request)
    }

    override suspend fun fetchRefreshToken(request: RefreshRequest): RefreshResponse =
        safeDataCall {
            remote.fetchRefreshToken(request = request)
        }

    override suspend fun fetchProfile(
        userName: RequestBody,
        userImage: MultipartBody.Part
    ): ProfileResponse {
        return safeDataCall {
            remote.fetchProfile(userName = userName, userImage = userImage)
        }
    }

    override suspend fun fetchProductLocal(): Flow<PagingData<ProductEntity>> = safeDataCall {
        paging.fetchProduct()
    }

    override suspend fun fetchDetailProduct(id: String): DetailResponse {
        return safeDataCall {
            remote.fetchDetailProduct(id = id)
        }
    }

    override suspend fun fetchReviewProduct(id: String): ReviewResponse {
        return safeDataCall {
            remote.fetchReviewProduct(id = id)
        }
    }

}