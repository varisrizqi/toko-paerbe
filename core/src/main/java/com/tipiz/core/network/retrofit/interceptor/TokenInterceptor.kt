package com.tipiz.core.network.retrofit.interceptor

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.tipiz.core.BuildConfig
import com.tipiz.core.local.pref.PrefDataStoreHelper
import com.tipiz.core.network.data.refresh.RefreshRequest
import com.tipiz.core.network.data.refresh.RefreshResponse
import com.tipiz.core.network.retrofit.ApiService
import com.tipiz.core.network.retrofit.interceptor.AuthInterceptor.Companion.api_key
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TokenInterceptor(
    private val prefs: PrefDataStoreHelper,
    private val chuckerInterceptor: ChuckerInterceptor
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = prefs.getRefreshToken()
        synchronized(this) {
            return runBlocking {
                try {
                    val newToken = refreshToken(RefreshRequest(refreshToken.first()))
                    prefs.setRefreshToken(newToken.data.refreshToken)
                    prefs.setAccessToken(newToken.data.accessToken)
                    response.request
                        .newBuilder()
                        .header(AuthInterceptor.authorization,"Bearer ${newToken.data.accessToken}")
                        .build()
                } catch (error: Throwable) {
                    response.close()
                    null
                }
            }
        }
    }

    private suspend fun refreshToken(tokenRequest: RefreshRequest): RefreshResponse {
        val interceptor = Interceptor.invoke { chain ->
            val request = chain
                .request()
                .newBuilder()
                .addHeader(api_key, BuildConfig.API_KEY)
                .build()
            chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(interceptor)
            .build()

        val apiService = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)

        try {
            val newRequest = apiService.fetchRefreshToken(tokenRequest)
            prefs.setAccessToken(newRequest.data.accessToken)
            prefs.setRefreshToken(newRequest.data.refreshToken)
            return newRequest
        } catch (e: Exception) {
            throw Exception(e.message)
        }

    }

}