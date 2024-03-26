package com.tipiz.toko_paerbe.ui.bottomnav.home

import androidx.lifecycle.ViewModel
import com.tipiz.core.domain.usecase.TokoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class HomeViewModel(private val useCase: TokoUseCase): ViewModel() {

    //===== Local =====

    fun clearSession(){
        runBlocking(Dispatchers.IO) {
            useCase.clearSession()
        }
    }
}