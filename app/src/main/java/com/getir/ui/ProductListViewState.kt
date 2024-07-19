package com.getir.ui

import com.getir.data.api.DemoResponse

data class ProductListViewState (
    val isLoading: Boolean? = null,
    val errorMessage: String? = null,
    val itemList: List<DemoResponse>? = null
)

