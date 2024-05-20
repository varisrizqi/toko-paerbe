package com.tipiz.core.domain.usecase

import androidx.paging.PagingData
import com.tipiz.core.data.network.data.fullfillmentbody.FulFillRequest
import com.tipiz.core.data.network.data.login.LoginRequest
import com.tipiz.core.data.network.data.register.RegisterRequest
import com.tipiz.core.domain.model.cart.DataCart
import com.tipiz.core.domain.model.favorite.DataFavorite
import com.tipiz.core.domain.model.fillfullment.DataFulFillMent
import com.tipiz.core.domain.model.login.DataLogin
import com.tipiz.core.domain.model.login.DataProfile
import com.tipiz.core.domain.model.login.DataToken
import com.tipiz.core.domain.model.products.DataDetailProduct
import com.tipiz.core.domain.model.products.DataProduct
import com.tipiz.core.domain.model.products.ProductsBody
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
    fun getUserId(): Flow<String>

    suspend fun clearSession()

    suspend fun setTheme(value: Boolean)

    fun getTheme(): Flow<Boolean>

    suspend fun setLocalize(value: String)

    fun getLocalize(): Flow<String>

    suspend fun resetAll()

    suspend fun setIslogin(value: Boolean)
    fun getIsLogin(): Flow<Boolean>

    //Remote Api
    suspend fun fetchRegister(request: RegisterRequest): DataToken
    suspend fun fetchLogin(request: LoginRequest): DataLogin
//    suspend fun fetchRefreshToken(request: RefreshRequest): DataToken

    suspend fun fetchProfile(
        userName: RequestBody,
        userImage: MultipartBody.Part
    ): DataProfile

    suspend fun fetchProductLocal(): Flow<UiState<PagingData<DataProduct>>>
    fun gitProduct(productsBody: ProductsBody?): Flow<PagingData<DataProduct>>


    /*
    * ORI
    * */
   suspend fun fetchDetailProduct(
         id:String
     ): DataDetailProduct

    suspend fun fetchReviewProduct(
        id: String
    ): List<DataReview>

    suspend fun fetchFulfillment(
        fulfillmentBody: FulFillRequest
    ) : DataFulFillMent

    // ROOM

    // ====== Favorite =====
     fun getAllFav() :Flow<List<DataFavorite>>
    suspend fun insertFav(fav: DataFavorite)
    suspend fun deleteItemFav(id: Int)
    suspend fun deleteWishlist(fav: String)
    fun getIsFav(id: String):Flow<Boolean>

    // ====== Chart =====
    suspend fun getAllChart(): Flow<List<DataCart>>
    suspend fun insertChart(chart: DataCart)
    fun updateCountChart(id: String, newCount: Int)
    suspend fun updateIsCheckedChart(id: String, newIsChecked: Boolean)
    suspend fun updateCheckAllChart(value: Boolean)
    suspend fun deleteItemChart(id: String)
    fun getStockChart(id: String): DataCart?
    suspend fun deleteCheckedChart()
    suspend fun addChart(dataChart: DataCart, action: Boolean): String
}