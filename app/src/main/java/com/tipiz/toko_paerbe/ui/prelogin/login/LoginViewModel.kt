package com.tipiz.toko_paerbe.ui.prelogin.login

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tipiz.core.data.network.data.login.LoginRequest
import com.tipiz.core.domain.model.login.DataLogin
import com.tipiz.core.domain.usecase.TokoUseCase
import com.tipiz.core.utils.state.UiState
import com.tipiz.toko_paerbe.ui.utils.toBase64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel(private val useCase: TokoUseCase) : ViewModel() {

    private val _responseLogin: MutableStateFlow<UiState<DataLogin>> =
        MutableStateFlow(UiState.Empty)
    val responseLogin = _responseLogin.asStateFlow()

    //====== local ======
    @RequiresApi(Build.VERSION_CODES.O)
     fun saveLogin(dataLogin: DataLogin) {
         runBlocking(Dispatchers.IO) {
             val toBase = dataLogin.userName.toBase64()
             useCase.setAccessToken(dataLogin.accessToken)
             useCase.setRefreshToken(dataLogin.refreshToken)
             useCase.setUserName(dataLogin.userName)
             useCase.setUserId(toBase)
         }

    }

    // ===== API ======

    /*
    * Jika ingin menggunakan state asMutableStateFlow
    */
   /* fun fetchLogin(request: LoginRequest) {
        viewModelScope.launch {
            _responseLogin.asMutableStateFlow { useCase.fetchLogin(request = request) }
        }
    }*/

   fun fetchLogin(request: LoginRequest){
        viewModelScope.launch {
            _responseLogin.value = UiState.Loading
            try {
                val success = useCase.fetchLogin(request = request)
                _responseLogin.value = UiState.Success(success)
            } catch (e:Exception){
                _responseLogin.value = UiState.Error(e)
            }
        }
    }


}