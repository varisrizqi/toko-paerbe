package com.tipiz.toko_paerbe.ui.prelogin.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tipiz.core.data.network.data.register.RegisterRequest
import com.tipiz.core.domain.model.login.DataToken
import com.tipiz.core.domain.usecase.TokoUseCase
import com.tipiz.core.utils.state.UiState
import com.tipiz.core.utils.state.asMutableStateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegisterViewModel(private val useCase: TokoUseCase) : ViewModel() {

    private val _responseRegister: MutableStateFlow<UiState<DataToken>> =
        MutableStateFlow(UiState.Empty)
    val responseRegister = _responseRegister.asStateFlow()

    // =========== Pref ==========================
    fun saveSession(token: DataToken) {
        runBlocking(Dispatchers.IO) {
            useCase.setAccessToken(token.accessToken)
            useCase.setRefreshToken(token.refreshToken)
        }
    }

    // =========== API ==========================
    fun fetchRegister(request: RegisterRequest) {
        viewModelScope.launch {
            _responseRegister.asMutableStateFlow {
                useCase.fetchRegister(request = request)
            }
        }
    }

}