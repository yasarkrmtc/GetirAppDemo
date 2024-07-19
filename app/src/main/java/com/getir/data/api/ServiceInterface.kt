package com.getir.data.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.POST

interface ServiceInterface {

    @GET("api/products")
    fun fetchProduct(
    ): Single<List<DemoResponse>>
}