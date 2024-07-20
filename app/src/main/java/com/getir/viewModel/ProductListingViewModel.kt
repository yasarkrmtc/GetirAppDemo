package com.getir.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.getir.data.api.Product
import com.getir.data.usecase.FetchProductUseCase
import com.getir.data.usecase.FetchSuggestedProductUseCase
import com.getir.data.usecase.GetTotalPriceUseCase
import com.getir.data.usecase.InsertDataBaseUseCase
import com.getir.model.ResponseStatus
import com.getir.ui.ProductListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListingViewModel @Inject constructor(
    private val fetchProductUseCase: FetchProductUseCase,
    private val fetchSuggestedProductUseCase: FetchSuggestedProductUseCase,
    private val insertDataBaseUseCase: InsertDataBaseUseCase,
    private val getTotalPriceUseCase: GetTotalPriceUseCase
) :
    ViewModel() {

    private val _viewState = MutableStateFlow(ProductListViewState())
    val viewState = _viewState.asStateFlow()

    private val _localPrice = MutableStateFlow("0.00")
    val localPrice = _localPrice.asStateFlow()

    fun getProduct() {
        viewModelScope.launch {
            fetchProductUseCase()
                .collect { response ->
                    when (response.status) {
                        ResponseStatus.LOADING -> _viewState.update { viewState ->
                            viewState.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }

                        ResponseStatus.SUCCESS -> _viewState.update { viewState ->
                            viewState.copy(
                                isLoading = false,
                                errorMessage = null,
                                productItemList = response.data
                            )
                        }

                        else -> {
                            _viewState.update { viewState ->
                                viewState.copy(
                                    isLoading = false,
                                    errorMessage = response.message
                                )
                            }
                        }
                    }
                }
        }

    }
    fun getSuggestedProduct() {
        viewModelScope.launch {
            fetchSuggestedProductUseCase()
                .collect { response ->
                    when (response.status) {
                        ResponseStatus.LOADING -> _viewState.update { viewState ->
                            viewState.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }

                        ResponseStatus.SUCCESS -> _viewState.update { viewState ->
                            viewState.copy(
                                isLoading = false,
                                errorMessage = null,
                                suggestedProductItemList = response.data
                            )
                        }

                        else -> {
                            _viewState.update { viewState ->
                                viewState.copy(
                                    isLoading = false,
                                    errorMessage = response.message
                                )
                            }
                        }
                    }
                }
        }

    }
    fun getTotalPrice() {
        viewModelScope.launch {
            getTotalPriceUseCase().collect {
                it?.let {
                    _localPrice.value = "₺" + String.format("%.2f", it)
                }
                if (it==null)_localPrice.value = "₺0.00"
            }
        }
    }

    fun updateDataBase(item: Product) {
        viewModelScope.launch {
            insertDataBaseUseCase(item)
        }
        getTotalPrice()
    }
}
