package com.tipiz.core.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.tipiz.core.data.network.data.login.LoginRequest
import com.tipiz.core.data.network.data.refresh.RefreshRequest
import com.tipiz.core.data.network.data.register.RegisterRequest
import com.tipiz.core.domain.model.login.DataLogin
import com.tipiz.core.domain.model.login.DataProfile
import com.tipiz.core.domain.model.login.DataToken
import com.tipiz.core.domain.model.products.DataDetailProduct
import com.tipiz.core.domain.model.products.DataProduct
import com.tipiz.core.domain.model.review.DataReview
import com.tipiz.core.domain.repository.TokoRepository
import com.tipiz.core.utils.DataMapper.toUIData
import com.tipiz.core.utils.DataMapper.toUiData
import com.tipiz.core.utils.DataMapper.toUiListData
import com.tipiz.core.utils.state.UiState
import com.tipiz.core.utils.state.safeDataCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
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


    override suspend fun setTheme(value: Boolean) {
        repo.setTheme(value)
    }
    override fun getTheme(): Flow<Boolean> = repo.getTheme()


    override suspend fun setLocalize(value: String) {
       repo.setLocalize(value)
    }

    override fun getLocalize(): Flow<String> = repo.getLocalize()
    override suspend fun resetAll() {
        repo.resetAll()
    }

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

    override suspend fun fetchProductLocal(): Flow<UiState<PagingData<DataProduct>>> {
        return withContext(Dispatchers.IO) {
            repo.fetchProductLocal().map { data ->
                val mapped = data.map { entity -> entity.toUIData() }
                UiState.Success(mapped)
            }.flowOn(Dispatchers.IO).catch { throwable -> UiState.Error(throwable) }
        }
    }

    override suspend fun fetchDetailProduct(id: String): DataDetailProduct {
        return withContext(Dispatchers.IO) {
            repo.fetchDetailProduct(id = id).data.toUIData()
        }
    }

    override suspend fun fetchReviewProduct(id: String): List<DataReview> {
        return withContext(Dispatchers.IO) {
            repo.fetchReviewProduct(id = id).toUiListData()
        }
    }
}