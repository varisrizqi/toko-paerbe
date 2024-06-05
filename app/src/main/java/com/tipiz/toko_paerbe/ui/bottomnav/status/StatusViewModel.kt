package com.tipiz.toko_paerbe.ui.bottomnav.status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tipiz.core.data.network.data.rating.RatingRequest
import com.tipiz.core.domain.model.rating.DataRating
import com.tipiz.core.domain.usecase.TokoUseCase
import com.tipiz.core.utils.state.UiState
import com.tipiz.core.utils.state.asMutableStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StatusViewModel(private val useCase: TokoUseCase): ViewModel() {

    private val _responseRating:MutableStateFlow<UiState<DataRating>> = MutableStateFlow((UiState.Empty))
     val responseRating = _responseRating.asStateFlow()

    var ratingBody = RatingRequest(
        invoiceId = null,
        rating = null,
        review = null
    )

    fun fetchStatus( ){
        viewModelScope.launch {
            _responseRating.asMutableStateFlow{
                useCase.fetchRating(ratingBody)

            }
        }
    }



}