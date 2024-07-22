package com.getir.data.remote

import com.getir.data.remote.ProductResponse
import com.getir.data.remote.SuggestedProductResponse
import retrofit2.Call
import retrofit2.http.GET

interface ServiceInterface {

    @GET("api/products")
    fun fetchProduct(
    ): Call<List<ProductResponse>>

    @GET("api/suggestedProducts")
    fun fetchSuggestedProduct(
    ): Call<List<SuggestedProductResponse>>
}