package com.getir.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.getir.data.repository.ItemEntity
import com.getir.data.usecase.GetLocalItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductBasketViewModel @Inject constructor(
    private val getLocalItemsUseCase: GetLocalItemsUseCase

) :
    ViewModel() {
    private val _items = MutableStateFlow<List<ItemEntity>>(emptyList())
    val items: StateFlow<List<ItemEntity>> = _items

    fun getLocalItems() {
        viewModelScope.launch {
            getLocalItemsUseCase().collect {
                _items.value = it
            }
        }
    }

}