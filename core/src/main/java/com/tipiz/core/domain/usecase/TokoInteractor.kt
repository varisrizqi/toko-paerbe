package com.tipiz.core.domain.usecase

import com.tipiz.core.domain.model.login.DataLogin
import com.tipiz.core.domain.model.login.DataProfile
import com.tipiz.core.domain.model.login.DataToken
import com.tipiz.core.domain.repository.TokoRepository
import com.tipiz.core.data.network.data.login.LoginRequest
import com.tipiz.core.data.network.data.refresh.RefreshRequest
import com.tipiz.core.data.network.data.register.RegisterRequest
import com.tipiz.core.utils.DataMapper.toUiData
import com.tipiz.core.utils.state.safeDataCall
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody


class TokoInteractor(
    private val repo: TokoRepository
) : TokoUseCase {
    // ============ Local DataStore  ============
    override suspend fun setOnBoarding(value: Boolean) {
        repo.setOnBoarding(value)
    }

    override fun getOnBoarding(): Flow<Boolean> = repo.getOnBoarding()

    override suspend fun setAccessToken(value: String) {
        repo.setAccessToken(value)
    }

    override fun getAccessToken(): Flow<String> = repo.getAccessToken()

    override suspend fun setRefreshToken(value: String) {
        repo.setRefreshToken(value)
    }

    override fun getRefreshToken(): Flow<String> = repo.getRefreshToken()

    override suspend fun setUserName(value: String) {
        repo.setUserName(value)
    }

    override fun getUserName(): Flow<String> = repo.getUserName()

    override suspend fun setUserId(value: String) {
        repo.setUserId(value)
    }

    override fun getUserId(): Flow<String> = repo.getUserId()
    override suspend fun clearSession() = repo.clearSession()
    // ============ Remote Api ============
    override suspend fun fetchRegister(request: RegisterRequest): DataToken = safeDataCall {
        repo.fetchRegister(request = request).toUiData()
    }

    override suspend fun fetchLogin(request: LoginRequest): DataLogin = safeDataCall {
        repo.fetchLogin(request = request).toUiData()
    }

    override suspend fun fetchRefreshToken(request: RefreshRequest): DataToken = safeDataCall {
        repo.fetchRefreshToken(request = request).toUiData()
    }

    override suspend fun fetchProfile(
        userName: RequestBody,
        userImage: MultipartBody.Part
    ): DataProfile = safeDataCall {
        repo.fetchProfile(userName = userName, userImage = userImage).toUiData()
    }
}