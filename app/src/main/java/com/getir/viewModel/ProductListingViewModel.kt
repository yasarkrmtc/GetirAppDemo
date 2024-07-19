package com.getir.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.getir.data.usecase.FetchProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListingViewModel @Inject constructor(private val fetchProductUseCase: FetchProductUseCase): ViewModel() {


    fun getProduct(){
        viewModelScope.launch {
            try {
                val response = fetchProductUseCase.invoke().blockingGet()
                Log.e("qqqqqqqqqq",response.toString())
            } catch (e: Exception) {
                Log.e("qqqqqqqqqqq", e.toString())
            }
        }
    }
}