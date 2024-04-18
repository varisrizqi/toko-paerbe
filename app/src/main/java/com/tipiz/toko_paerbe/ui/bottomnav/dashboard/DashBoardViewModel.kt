package com.tipiz.toko_paerbe.ui.bottomnav.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.tipiz.core.domain.usecase.TokoUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class DashBoardViewModel(
    private val useCase: TokoUseCase
) : ViewModel() {

    fun getUserName(): String {
        return runBlocking {
            useCase.getUserName().first()
        }
    }

    fun getAccessToken(): String {
        return runBlocking {
            useCase.getAccessToken().first()
        }
    }

    fun getIsLogin()= useCase.getIsLogin().asLiveData()

}