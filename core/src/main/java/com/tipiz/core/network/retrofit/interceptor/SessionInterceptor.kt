package com.tipiz.core.network.retrofit.interceptor

import com.tipiz.core.local.pref.PrefDataStoreHelper
import okhttp3.Interceptor
import okhttp3.Response

class SessionInterceptor(private val prefs:PrefDataStoreHelper): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val response = chain.proceed(request)

//        if (response.code == 401){
//           prefs.cl
//        }
        return response
    }
}