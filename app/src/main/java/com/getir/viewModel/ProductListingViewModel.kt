package com.getir.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.getir.data.usecase.FetchProductUseCase
import com.getir.model.ResponseStatus
import com.getir.ui.ProductListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListingViewModel @Inject constructor(private val fetchProductUseCase: FetchProductUseCase) :
    ViewModel() {

    private val _viewState = MutableStateFlow(ProductListViewState())
    val viewState = _viewState.asStateFlow()

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
                                itemList = response.data
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
}
