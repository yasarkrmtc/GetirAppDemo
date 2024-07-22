package com.getir.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.getir.data.remote.Product
import com.getir.data.local.ItemEntity
import com.getir.domain.usecase.GetPrdouctUseCase
import com.getir.domain.usecase.GetTotalPriceUseCase
import com.getir.domain.usecase.InsertDataBaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
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