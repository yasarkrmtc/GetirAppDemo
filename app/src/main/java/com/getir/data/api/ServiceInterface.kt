package com.getir.data.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.POST

interface ServiceInterface {

    @POST("api/products")
    fun fetchProduct(
    ): Single<DemoResponse>
}