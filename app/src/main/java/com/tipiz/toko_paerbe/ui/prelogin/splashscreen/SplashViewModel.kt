package com.tipiz.toko_paerbe.ui.prelogin.splashscreen

import androidx.lifecycle.ViewModel
import com.tipiz.core.domain.model.login.DataSession
import com.tipiz.core.domain.model.login.DataToken
import com.tipiz.core.domain.usecase.TokoUseCase
import com.tipiz.core.utils.DataMapper.toSplashState
import com.tipiz.core.utils.state.SplashState
import com.tipiz.core.utils.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class SplashViewModel(private val pref: TokoUseCase) : ViewModel() {


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

    fun getOnBoardingValue(){
        runBlocking {
            val sessionData = pref.getSessionData().first()
            val splashState = sessionData.toSplashState()
            _onBoarding.value = splashState
        }
    }


    private val _response: MutableStateFlow<UiState<DataToken>> = MutableStateFlow(UiState.Empty)
    val response = _response.asStateFlow()

    // =========== Pref ==========================
    suspend fun setAccessToken1(value: String) {
        pref.setAccessToken(value)
    }

    suspend fun setRefreshToken(value: String) {
        pref.setRefreshToken(value)
    }

    suspend fun saveSession(token: DataToken) {
        pref.setRefreshToken(token.refreshToken)
        pref.setAccessToken(token.accessToken)
    }

    // =========== API ==========================
    /*fun fetchRegister(request: RegisterRequest) {
        viewModelScope.launch {
            _response.asMutableStateFlow {
                pref.fetchRegister(request = request)
            }
        }
    }*/

}