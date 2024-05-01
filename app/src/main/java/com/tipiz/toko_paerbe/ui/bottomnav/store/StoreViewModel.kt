package com.tipiz.toko_paerbe.ui.bottomnav.store

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.tipiz.core.domain.model.products.ProductsBody
import com.tipiz.core.domain.model.review.DataReview
import com.tipiz.core.domain.usecase.TokoUseCase
import com.tipiz.core.utils.state.UiState
import com.tipiz.core.utils.state.asMutableStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class StoreViewModel(private val useCase: TokoUseCase) :
    ViewModel() {

    private val _responseReview: MutableStateFlow<UiState<List<DataReview>>> =
        MutableStateFlow((UiState.Empty))
    val responseReview = _responseReview.asStateFlow()


    /*
    * menyimpan data ke room tapi tanpa cachedIn()
    * */
    fun fetchProduct() = runBlocking { useCase.fetchProductLocal() }

    /*
   * dengan menyimpan ke cachedIn ,
   * jadi ketika ke home lalu ke store kembali maka tetap berada di paging sebelumnya
   **/
    var isGridLayout = false
    val productsBody = MutableLiveData(
        ProductsBody(
            null,
            null,
            null,
            null,
            null,
            10,
            1
        )
    )

    val products = productsBody.switchMap { query ->
        useCase.gitProduct(query).cachedIn(viewModelScope).asLiveData()
    }

//    @OptIn(ExperimentalCoroutinesApi::class)
//    val products = _productsBody.flatMapLatest { _ ->
//        useCase.gitProduct()
//    }

    // ====== bottom sheet ======

    fun updateFilter(sort: String?, category: String?, lowest: Int?, highest: Int?) {
        runBlocking {
            productsBody.postValue(
                productsBody.value?.copy(
                    sort = sort,
                    brand = category,
                    lowest = lowest,
                    highest = highest
                )
            )

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
