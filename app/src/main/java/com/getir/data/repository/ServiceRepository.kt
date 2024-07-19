package com.getir.data.repository

import com.getir.data.api.DemoResponse
import io.reactivex.rxjava3.core.Single

interface ServiceRepository {
    suspend fun getProducts(): Single<List<DemoResponse>>

}