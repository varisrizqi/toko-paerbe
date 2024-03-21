package com.tipiz.toko_paerbe.ui.prelogin.login

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tipiz.core.domain.model.login.DataLogin
import com.tipiz.core.domain.usecase.TokoUseCase
import com.tipiz.core.network.data.login.LoginRequest
import com.tipiz.core.utils.state.UiState
import com.tipiz.core.utils.state.asMutableStateFlow
import com.tipiz.toko_paerbe.ui.utils.toBase64
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val useCase: TokoUseCase) : ViewModel() {

    private val _responseLogin: MutableStateFlow<UiState<DataLogin>> =
        MutableStateFlow(UiState.Empty)
    val responseLogin = _responseLogin.asStateFlow()

    //local
    suspend fun setUserName(value:String){
        useCase.setUserName(value)
    }

    @RequiresApi(Build.VERSION_CODES.O)
     fun saveLogin(dataLogin:DataLogin){
         viewModelScope.launch {
             val toBase = dataLogin.userName.toBase64()
             useCase.setAccessToken(dataLogin.accessToken)
             useCase.setRefreshToken(dataLogin.refreshToken)
             useCase.setUserName(dataLogin.userName)
             useCase.setUserId(toBase)
         }

    }

    fun fetchLogin(request: LoginRequest) {
        viewModelScope.launch {
            _responseLogin.asMutableStateFlow { useCase.fetchLogin(request = request) }
        }
    }

}