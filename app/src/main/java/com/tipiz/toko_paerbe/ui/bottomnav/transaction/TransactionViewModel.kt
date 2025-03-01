package com.tipiz.toko_paerbe.ui.bottomnav.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tipiz.core.domain.model.transaction.DataTransaction
import com.tipiz.core.domain.usecase.TokoUseCase
import com.tipiz.core.utils.state.UiState
import com.tipiz.core.utils.state.asMutableStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TransactionViewModel(private val useCase: TokoUseCase) : ViewModel() {

    private val _responseTransaction: MutableStateFlow<UiState<List<DataTransaction>>> =
        MutableStateFlow(UiState.Empty)
    val responseTransaction = _responseTransaction.asStateFlow()

    fun fetchTransaction() {
        viewModelScope.launch {
            _responseTransaction.asMutableStateFlow {
                useCase.fetchTransaction()
            }
        }
    }

    init {
        fetchTransaction()
    }
}