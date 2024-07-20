package com.getir.ui

import com.getir.data.api.ProductResponse
import com.getir.data.api.SuggestedProductResponse

data class ProductListViewState (
    val isLoading: Boolean? = null,
    val errorMessage: String? = null,
    val productItemList: List<ProductResponse>? = null,
    val suggestedProductItemList: List<SuggestedProductResponse>? = null

)

