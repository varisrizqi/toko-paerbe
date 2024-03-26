package com.tipiz.toko_paerbe.ui.prelogin.splashscreen

import androidx.lifecycle.ViewModel
import com.tipiz.core.domain.usecase.TokoUseCase
import kotlinx.coroutines.flow.first

class SplashViewModel(private val pref: TokoUseCase) : ViewModel() {

    suspend fun setOnBoarding(value: Boolean) {
        pref.setOnBoarding(value)
    }

    suspend fun getOnBoarding(): Boolean {
        return pref.getOnBoarding().first()
    }

    suspend fun getAccessToken(): String {
        return pref.getAccessToken().first()
    }

    suspend fun getUserName(): String {
        return pref.getUserName().first()
    }

}