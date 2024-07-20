package com.getir.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.getir.data.api.Product
import com.getir.data.repository.ItemEntity
import com.getir.data.usecase.GetLocalItemsUseCase
import com.getir.data.usecase.GetPrdouctUseCase
import com.getir.data.usecase.GetTotalPriceUseCase
import com.getir.data.usecase.InsertDataBaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getLocalItemsUseCase: GetLocalItemsUseCase,
    private val getTotalPriceUseCase: GetTotalPriceUseCase,
    private val insertDataBaseUseCase: InsertDataBaseUseCase,
    private val getPrdouctUseCase: GetPrdouctUseCase

) :
    ViewModel() {
    private val _item = MutableStateFlow<ItemEntity?>(null)
    val item: StateFlow<ItemEntity?> = _item

    private val _localPrice = MutableStateFlow("₺0.00")
    val localPrice = _localPrice.asStateFlow()


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
            getProduct(id = item.id)
            getTotalPrice()
        }
    }

    fun getProduct(id:String){
        viewModelScope.launch {
            _item.value = getPrdouctUseCase(id)
        }
    }

}