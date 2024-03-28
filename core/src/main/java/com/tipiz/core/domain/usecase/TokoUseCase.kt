package com.tipiz.core.domain.usecase

import androidx.paging.PagingData
import com.tipiz.core.data.network.data.login.LoginRequest
import com.tipiz.core.data.network.data.refresh.RefreshRequest
import com.tipiz.core.data.network.data.register.RegisterRequest
import com.tipiz.core.domain.model.login.DataLogin
import com.tipiz.core.domain.model.login.DataProfile
import com.tipiz.core.domain.model.login.DataToken
import com.tipiz.core.domain.model.products.DataDetailProduct
import com.tipiz.core.domain.model.products.DataProduct
import com.tipiz.core.domain.model.review.DataReview
import com.tipiz.core.utils.state.UiState
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

    suspend fun setTheme(value:Boolean)

    fun getTheme ():Flow<Boolean>

    suspend fun setLocalize(value:String)

    fun getLocalize():Flow<String>

    suspend fun resetAll()


    //Remote Api
    suspend fun fetchRegister(request: RegisterRequest): DataToken
    suspend fun fetchLogin(request: LoginRequest): DataLogin
    suspend fun fetchRefreshToken(request: RefreshRequest): DataToken

    suspend fun fetchProfile(
        userName: RequestBody,
        userImage: MultipartBody.Part
    ): DataProfile

    suspend fun fetchProductLocal(): Flow<UiState<PagingData<DataProduct>>>

    suspend fun fetchDetailProduct(
        id:String
    ): DataDetailProduct

    suspend fun fetchReviewProduct(
        id:String
    ): List<DataReview>
}