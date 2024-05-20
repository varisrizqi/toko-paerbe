package com.tipiz.toko_paerbe.ui.bottomnav.checkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tipiz.core.data.network.data.fullfillmentbody.FulFillRequest
import com.tipiz.core.data.network.data.fullfillmentbody.ItemsItemFillFull
import com.tipiz.core.domain.model.cart.DataCart
import com.tipiz.core.domain.model.fillfullment.DataFulFillMent
import com.tipiz.core.domain.usecase.TokoUseCase
import com.tipiz.core.utils.state.UiState
import com.tipiz.core.utils.state.asMutableStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CheckoutViewModel(private val useCase: TokoUseCase) : ViewModel() {

    private val _responseFullFillMent: MutableStateFlow<UiState<DataFulFillMent>> =
        MutableStateFlow(UiState.Empty)
    val responseFullFillMent = _responseFullFillMent.asStateFlow()

    var checkoutProduct = MutableLiveData<List<DataCart>>()

     var fBody = MutableLiveData(
        FulFillRequest(
            payment = null,
            items = listOf(null, null, null)
        )
    )

    fun fetchFulfillment() {
        viewModelScope.launch {
            _responseFullFillMent.asMutableStateFlow {
                useCase.fetchFulfillment(fBody.value!!)
            }
        }
    }

    fun addItemToBuy(items: List<ItemsItemFillFull?>){
        fBody.postValue(
            fBody.value?.copy(items = items)
        )
    }

    fun addPaymentMethod(payment:String?){
        fBody.value?.payment = payment
    }
}