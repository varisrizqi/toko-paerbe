package com.tipiz.core.data.network.retrofit.interceptor

import com.tipiz.core.data.local.datastore.PrefDataStoreHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class SessionInterceptor(private val prefs: PrefDataStoreHelper) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code == 401) {
            runBlocking(Dispatchers.IO) {
                prefs.clearSession()
            }
        }
        return response
    }
}