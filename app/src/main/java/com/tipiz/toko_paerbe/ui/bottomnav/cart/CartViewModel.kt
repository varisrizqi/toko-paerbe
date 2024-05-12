package com.tipiz.toko_paerbe.ui.bottomnav.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tipiz.core.domain.model.cart.DataCart
import com.tipiz.core.domain.usecase.TokoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CartViewModel(private val useCase: TokoUseCase) : ViewModel() {

    var checkoutItems = mutableListOf<DataCart>()

    val getAllChart = runBlocking { useCase.getAllChart().asLiveData() }

    // + -
    fun updateCart(data: DataCart, state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.addChart(data, state)
        }
    }

    //keranjang sampah (delete)
    fun deleteItemChart(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.deleteItemChart(id)
        }
    }

    //check box all
    fun allCheck(state:Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.updateCheckAllChart(state)
        }
    }

    // check box per item
    fun updateIsChecked(id:String,state:Boolean){
        viewModelScope.launch(Dispatchers.IO){
            useCase.updateIsCheckedChart(id,state)
        }
    }

    // btn delete all
    fun deleteAllChecked(){
        viewModelScope.launch(Dispatchers.IO){
            useCase.deleteCheckedChart()
        }
    }


}
