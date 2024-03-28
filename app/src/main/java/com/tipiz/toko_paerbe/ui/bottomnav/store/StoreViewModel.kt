package com.tipiz.toko_paerbe.ui.bottomnav.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tipiz.core.domain.model.products.DataDetailProduct
import com.tipiz.core.domain.model.review.DataReview
import com.tipiz.core.domain.usecase.TokoUseCase
import com.tipiz.core.utils.state.UiState
import com.tipiz.core.utils.state.asMutableStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class StoreViewModel(private val useCase: TokoUseCase) : ViewModel() {

    private val _responseDetail: MutableStateFlow<UiState<DataDetailProduct>> =
        MutableStateFlow((UiState.Empty))
    val responseDetail = _responseDetail.asStateFlow()

    private val _responseReview: MutableStateFlow<UiState<List<DataReview>>> =
        MutableStateFlow((UiState.Empty))
    val responseReview = _responseReview.asStateFlow()

    fun fetchProduct() = runBlocking { useCase.fetchProductLocal() }

    fun detailProducts(
        id: String
    ) {
        viewModelScope.launch {
            _responseDetail.asMutableStateFlow {
                useCase.fetchDetailProduct(id = id)
            }
        }
    }

    fun showReviewProducts(id: String) {
        viewModelScope.launch {
            _responseReview.asMutableStateFlow {
                useCase.fetchReviewProduct(id = id)
            }
        }
    }


}