package com.tipiz.core.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.tipiz.core.data.network.data.fullfillmentbody.FulFillRequest
import com.tipiz.core.data.network.data.login.LoginRequest
import com.tipiz.core.data.network.data.register.RegisterRequest
import com.tipiz.core.domain.model.cart.DataCart
import com.tipiz.core.domain.model.favorite.DataFavorite
import com.tipiz.core.domain.model.fillfullment.DataFulFillMent
import com.tipiz.core.domain.model.firebase.Notification
import com.tipiz.core.domain.model.firebase.PromoFcm
import com.tipiz.core.domain.model.login.DataLogin
import com.tipiz.core.domain.model.login.DataProfile
import com.tipiz.core.domain.model.login.DataToken
import com.tipiz.core.domain.model.products.DataDetailProduct
import com.tipiz.core.domain.model.products.DataProduct
import com.tipiz.core.domain.model.products.ProductsBody
import com.tipiz.core.domain.model.review.DataReview
import com.tipiz.core.domain.repository.TokoRepository
import com.tipiz.core.utils.Constant.CART_ADDED
import com.tipiz.core.utils.Constant.CART_DECREASED
import com.tipiz.core.utils.Constant.CART_FULL
import com.tipiz.core.utils.Constant.CART_MINIMUM
import com.tipiz.core.utils.DataMapper.toChartEntity
import com.tipiz.core.utils.DataMapper.toDataChart
import com.tipiz.core.utils.DataMapper.toDataFull
import com.tipiz.core.utils.DataMapper.toEntity
import com.tipiz.core.utils.DataMapper.toEntityNotification
import com.tipiz.core.utils.DataMapper.toUIData
import com.tipiz.core.utils.DataMapper.toUiChartData
import com.tipiz.core.utils.DataMapper.toUiData
import com.tipiz.core.utils.DataMapper.toUiDataNotify
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

    override suspend fun setIslogin(value: Boolean) {
        repo.setIslogin(value)
    }

    override fun getIsLogin(): Flow<Boolean> {
        return repo.getIsLogin()
    }

    // ============ Remote Api ============
    override suspend fun fetchRegister(request: RegisterRequest): DataToken = safeDataCall {
        repo.fetchRegister(request = request).toUiData()
    }

    override suspend fun fetchLogin(request: LoginRequest): DataLogin = safeDataCall {
        repo.fetchLogin(request = request).toUiData()
    }

//    override suspend fun fetchRefreshToken(request: RefreshRequest): DataToken = safeDataCall {
//        repo.fetchRefreshToken(request = request).toUiData()
//    }

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

    override fun gitProduct(productsBody: ProductsBody?): Flow<PagingData<DataProduct>> {
        return repo.gitProduct(productsBody)
    }

    /*
   * ORI
   * */
    override suspend fun fetchDetailProduct(id: String): DataDetailProduct = safeDataCall {
        repo.fetchDetailProduct(id).toUIData()
    }

    /*override suspend fun fetchDetailProduct(id: String?): Flow<UiState<DataDetailProduct>> {
        return withContext(Dispatchers.IO){
            repo.fetchDetailProduct(id).map { data->
                val mapped = data.toUiData()
                UiState.Success(mapped)
            }.flowOn(Dispatchers.IO).catch { throwable -> UiState.Error(throwable) }
        }
    }*/

    override suspend fun fetchReviewProduct(id: String): List<DataReview> = safeDataCall {
        repo.fetchReviewProduct(id = id).toUiListData()
    }

    override suspend fun fetchFulfillment(fulfillmentBody: FulFillRequest): DataFulFillMent =
        safeDataCall {
            repo.fetchFulfillment(fulfillmentBody = fulfillmentBody).toDataFull()
        }
    // ROOM

    // ======= FAVORITE ======
    override fun getAllFav(): Flow<List<DataFavorite>> {
        return repo.getAllFav().map { it.toUiData() }
    }

    override suspend fun insertFav(fav: DataFavorite) {
        repo.insertFav(fav.toEntity())
    }

    override suspend fun deleteItemFav(id: Int) {
        repo.deleteItemFav(id)
    }

    override suspend fun deleteWishlist(fav: String) {
        repo.deleteWishlist(fav)
    }

    override fun getIsFav(id: String): Flow<Boolean> {
        return repo.getIsFav(id)

    }

    // ======= Chart ======

    override suspend fun getAllChart(): Flow<List<DataCart>> = safeDataCall {
        repo.getAllChart().map { it.toUiChartData() }
    }

    override suspend fun insertChart(chart: DataCart) {
        repo.insertChart(chart.toChartEntity(1, false))
    }

    override fun updateCountChart(id: String, newCount: Int) {
        repo.updateCountChart(id, newCount)
    }

    override suspend fun updateIsCheckedChart(id: String, newIsChecked: Boolean) {
        repo.updateIsCheckedChart(id, newIsChecked)
    }

    override suspend fun updateCheckAllChart(value: Boolean) {
        repo.updateCheckAllChart(value)
    }

    override suspend fun deleteItemChart(id: String) {
        repo.deleteItemChart(id)
    }

    override fun getStockChart(id: String): DataCart? {
        return repo.getStockChart(id)?.toDataChart()

    }

    override suspend fun deleteCheckedChart() {
        repo.deleteCheckedChart()
    }

    /*
    *  digunakan untuk menampilkan atau menambah dan memperbarui jumlah count
    * */
    override suspend fun addChart(dataChart: DataCart, action: Boolean): String {
        val chart = repo.getStockChart(dataChart.productId)?.toDataChart()
        if (chart != null) {
            return when (action) {
                true -> {
                    if (chart.amount == chart.stock) {
                        CART_FULL
                    } else {
                        chart.amount += 1
                        repo.updateCountChart(dataChart.productId, chart.amount)
                        CART_ADDED
                    }
                }

                false -> {
                    if (chart.amount == 1) {
                        CART_MINIMUM
                    } else {
                        chart.amount -= 1
                        repo.updateCountChart(dataChart.productId, chart.amount)
                        CART_DECREASED
                    }
                }
            }
        } else {
            repo.insertChart(dataChart.toChartEntity(1, false))
            return CART_ADDED
        }
    }

    // notify
    override fun getAllNotification(): Flow<List<Notification>> {
        return repo.getAllNotification().map { data -> data.toUiDataNotify() }
    }

    override suspend fun insertNotification(notification: PromoFcm) {
        repo.insertNotification(notification.toEntityNotification())
    }

    override suspend fun updateIsCheckedNotification(id: Int, newIsChecked: Boolean) {
        repo.updateIsCheckedNotification(id = id, newIsChecked = newIsChecked)
    }
}