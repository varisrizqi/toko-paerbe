package com.tipiz.toko_paerbe.ui.prelogin.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tipiz.core.domain.model.login.DataProfile
import com.tipiz.core.domain.usecase.TokoUseCase
import com.tipiz.core.utils.state.UiState
import com.tipiz.core.utils.state.asMutableStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProfileViewModel(private val useCase: TokoUseCase) : ViewModel() {

    private val _responseProfile: MutableStateFlow<UiState<DataProfile>> = MutableStateFlow((UiState.Empty))
    val responseProfile = _responseProfile.asStateFlow()

    //===== local =====
    suspend fun setUserName(value:String){
        useCase.setUserName(value)
    }

    // ===== API =====
    fun fetchProfile(userName: RequestBody, userImage: MultipartBody.Part) {
        viewModelScope.launch {
            _responseProfile.asMutableStateFlow {
                useCase.fetchProfile(userName = userName, userImage = userImage)
            }
        }
    }

}