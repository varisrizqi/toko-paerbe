package com.tipiz.toko_paerbe.ui.bottomnav.dashboard

import androidx.lifecycle.ViewModel
import com.tipiz.core.domain.usecase.TokoUseCase
import kotlinx.coroutines.flow.first

class DashBoardViewModel(private val useCase: TokoUseCase):ViewModel() {

     suspend fun getUserName():String{
         return useCase.getUserName().first()
    }
}