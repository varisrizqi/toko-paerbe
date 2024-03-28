package com.tipiz.core.data.network.retrofit

import com.tipiz.core.data.network.data.login.LoginRequest
import com.tipiz.core.data.network.data.login.LoginResponse
import com.tipiz.core.data.network.data.products.ProductsResponse
import com.tipiz.core.data.network.data.profile.ProfileResponse
import com.tipiz.core.data.network.data.refresh.RefreshRequest
import com.tipiz.core.data.network.data.refresh.RefreshResponse
import com.tipiz.core.data.network.data.register.RegisterRequest
import com.tipiz.core.data.network.data.register.RegisterResponse
import com.tipiz.core.remote.data.detail.DetailResponse
import com.tipiz.core.remote.data.review.ReviewResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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

    @POST("products")
    suspend fun fetchProduct(
        @Query("search") search: String? = null,
        @Query("brand") brand: String? = null,
        @Query("lowest") lowest: Int? = null,
        @Query("highest") highest: Int? = null,
        @Query("sort") sort: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("page") page: Int? = null,
    ): ProductsResponse

    @GET("products/{id}")
    suspend fun fetchDetailProduct(
        @Path("id") id: String
    ): DetailResponse

    @GET("review/{id}")
    suspend fun fetchReviewProduct(
        @Path("id") id: String
    ): ReviewResponse


}