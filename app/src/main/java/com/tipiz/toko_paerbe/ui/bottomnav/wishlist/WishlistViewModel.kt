package com.tipiz.toko_paerbe.ui.bottomnav.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tipiz.core.domain.usecase.TokoUseCase
import kotlinx.coroutines.launch

class WishlistViewModel(private val useCase: TokoUseCase) : ViewModel() {

    var isGridLayout = false

    private var _stock: MutableLiveData<Int?> = MutableLiveData<Int?>()
    val stock: LiveData<Int?> = _stock

    // Database
    fun deleteFav(id: String) {
        viewModelScope.launch {
            useCase.deleteItemFav(id)
        }
    }


//    suspend fun addChart(product: Product, action: Boolean): String {
//        return repository.addChart(product, action)
//    }

    fun getAllFav() = useCase.getAllFav().asLiveData()
}