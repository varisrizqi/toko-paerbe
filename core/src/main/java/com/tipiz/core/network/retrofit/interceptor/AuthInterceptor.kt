package com.tipiz.core.network.retrofit.interceptor

import com.tipiz.core.BuildConfig
import com.tipiz.core.local.pref.PrefDataStoreHelper
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val pref: PrefDataStoreHelper) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val modifiedRequest = when (request.url.encodedPath) {
            endpoint_register, endpoint_login, endpoint_refresh -> {
                request.newBuilder()
                    .addHeader(api_key, BuildConfig.API_KEY)
                    .build()
            }

            else -> {
                val accessToken = pref.getAccessToken()
                request.newBuilder()
                    .addHeader(authorization, "Bearer $accessToken")
                    .build()
            }
        }
        return chain.proceed(modifiedRequest)

    }

    companion object {
        const val endpoint_register = "/register"
        const val endpoint_login = "/login"
        const val endpoint_refresh = "/refresh"
        private const val api_key = "api_key"
        private const val authorization = "Authorization"
    }
}