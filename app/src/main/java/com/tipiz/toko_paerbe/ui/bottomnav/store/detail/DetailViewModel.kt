package com.tipiz.toko_paerbe.ui.bottomnav.store.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tipiz.core.domain.model.favorite.DataFavorite
import com.tipiz.core.domain.model.products.DataDetailProduct
import com.tipiz.core.domain.usecase.TokoUseCase
import com.tipiz.core.utils.state.UiState
import com.tipiz.core.utils.state.asMutableStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DetailViewModel(private val useCase: TokoUseCase) :
    ViewModel() {


    // ====== Detail product =====

    private val _responseDetail: MutableStateFlow<UiState<DataDetailProduct>> =
        MutableStateFlow((UiState.Empty))
    val responseDetail = _responseDetail.asStateFlow()

    fun detailProducts(id: String) {
        viewModelScope.launch {
            _responseDetail.asMutableStateFlow {
                useCase.fetchDetailProduct(id = id)
            }
        }
    }


    // ========== FAV  ==========

    private var dataFavorite: DataFavorite? = null
    fun setDataFavorite(data: DataFavorite) {
        dataFavorite = data
    }


    var isFav: Boolean = false

    fun insertFav() {
        viewModelScope.launch {
            dataFavorite?.let {
                useCase.insertFav(dataFavorite!!)
            }
        }
    }

    fun getIsFav(id: String) = runBlocking { useCase.getIsFav(id).asLiveData() }
    fun deleteFav(id: String) {
        viewModelScope.launch {
            useCase.deleteItemFav(id)
        }
    }
}
