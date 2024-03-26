package com.tipiz.core.data.network.retrofit

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.tipiz.core.BuildConfig
import com.tipiz.core.data.network.retrofit.interceptor.AuthInterceptor
import com.tipiz.core.data.network.retrofit.interceptor.SessionInterceptor
import com.tipiz.core.data.network.retrofit.interceptor.TokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkClient(
    val authInterceptor: AuthInterceptor,
    val sessionInterceptor: SessionInterceptor,
    val tokenInterceptor: TokenInterceptor,
    val chuckerInterceptor: ChuckerInterceptor
) {

    inline fun <reified I> create(): I {
        val okHttpClient =  OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(authInterceptor)
            .addInterceptor(sessionInterceptor)
            .addInterceptor(chuckerInterceptor)
            .authenticator(tokenInterceptor)
            .connectTimeout(timeout = 120, TimeUnit.SECONDS)
            .readTimeout(timeout = 120, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(I::class.java)
    }
}