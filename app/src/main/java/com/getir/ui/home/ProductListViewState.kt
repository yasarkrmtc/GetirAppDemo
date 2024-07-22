package com.getir.ui.home

import com.getir.data.remote.ProductResponse
import com.getir.data.remote.SuggestedProductResponse

data class ProductListViewState (
    val isLoading: Boolean? = null,
    val errorMessage: String? = null,
    val productItemList: List<ProductResponse>? = null,
    val suggestedProductItemList: List<SuggestedProductResponse>? = null
)