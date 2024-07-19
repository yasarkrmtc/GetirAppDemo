package com.getir.data.api

import retrofit2.Call
import retrofit2.http.GET

interface ServiceInterface {

    @GET("api/products")
    fun fetchProduct(
    ): Call<List<DemoResponse>>

}