package com.getir.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.getir.data.api.Product
import com.getir.data.api.ProductResponse
import com.getir.data.repository.ItemEntity
import com.getir.data.usecase.DeleteAllItemsUseCase
import com.getir.data.usecase.FetchProductUseCase
import com.getir.data.usecase.GetLocalItemsUseCase
import com.getir.data.usecase.GetTotalPriceUseCase
import com.getir.data.usecase.InsertDataBaseUseCase
import com.getir.model.ResponseStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductBasketViewModel @Inject constructor(
    private val getLocalItemsUseCase: GetLocalItemsUseCase,
    private val getTotalPriceUseCase: GetTotalPriceUseCase,
    private val insertDataBaseUseCase: InsertDataBaseUseCase,
    private val deleteAllItemsUseCase: DeleteAllItemsUseCase,
    private val fetchProductUseCase: FetchProductUseCase,
) : ViewModel() {
    private val _items = MutableStateFlow<List<ItemEntity>>(emptyList())
    val items: StateFlow<List<ItemEntity>> = _items

    private val _suggestedItemList = MutableStateFlow<List<ProductResponse>?>(null)
    val suggestedItemList = _suggestedItemList.asStateFlow()

    private val _localPrice = MutableStateFlow("₺0.00")
    val localPrice = _localPrice.asStateFlow()

    fun getLocalItems() {
        viewModelScope.launch {
            getLocalItemsUseCase().collect {
                _items.value = it
            }
        }
    }

    fun getTotalPrice() {
        viewModelScope.launch {
            getTotalPriceUseCase().collect {
                it?.let {
                    _localPrice.value = "₺" + String.format("%.2f", it)
                }
                if (it == null) _localPrice.value = "₺0.00"
            }
        }
    }

    fun updateDataBase(item: Product) {
        viewModelScope.launch {
            insertDataBaseUseCase(item)
            getLocalItems()
            getTotalPrice()
        }

    }

    fun clearDatabase() {
        viewModelScope.launch {
            deleteAllItemsUseCase()
            getLocalItems()
            getTotalPrice()
        }
    }

    fun getProduct() {
        viewModelScope.launch {
            fetchProductUseCase().collect { response ->
                when (response.status) {
                    ResponseStatus.LOADING ->
                        _suggestedItemList.value = null

                    ResponseStatus.SUCCESS -> {
                        val existingIds = _items.value.map { it.id }.toSet()

                        val updatedProductList = response.data?.mapNotNull { productResponse ->
                            productResponse.products = productResponse.products?.filterNot { product ->
                                existingIds.contains(product.id)
                            }
                            if (productResponse.products.isNullOrEmpty()) null else productResponse
                        }
                        _suggestedItemList.value = updatedProductList

                    }

                    else -> {
                        _suggestedItemList.value = null

                    }
                }
            }
        }
    }
}