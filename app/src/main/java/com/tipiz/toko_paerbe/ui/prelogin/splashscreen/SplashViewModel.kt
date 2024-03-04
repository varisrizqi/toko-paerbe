package com.tipiz.toko_paerbe.ui.prelogin.splashscreen

import androidx.lifecycle.ViewModel
import com.tipiz.core.domain.model.DataSession
import com.tipiz.core.domain.repository.onboardingrepo.OnBoardingRepositoryImpl
import com.tipiz.core.utils.state.SplashState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class SplashViewModel(private val pref: OnBoardingRepositoryImpl) : ViewModel() {


    private val _onBoarding = MutableStateFlow<SplashState<DataSession>>(SplashState.OnBoarding)
    val onBoarding = _onBoarding.asStateFlow()


    fun setOnBoarding(value: Boolean) {
        runBlocking {
            pref.setOnBoarding(value)
        }
    }


    fun getOnBoarding(): Boolean {
        return runBlocking {
            pref.getOnBoarding().first()
        }
    }

//    fun getOnBoardingState() {
//        _onBoarding.update { pref.dataSession().toSplashState() }
//    }


}