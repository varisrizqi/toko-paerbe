package com.tipiz.toko_paerbe.ui.bottomnav.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tipiz.core.domain.usecase.TokoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationViewModel(private val useCase: TokoUseCase):ViewModel() {

    fun getPromoNotification() = useCase.getAllNotification().asLiveData()

    fun updateIsCheck(id: Int, value: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.updateIsCheckedNotification(id, value)
        }
    }


}