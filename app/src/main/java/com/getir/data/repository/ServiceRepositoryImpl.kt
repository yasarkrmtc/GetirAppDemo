package com.getir.data.repository

import com.getir.data.api.DemoResponse
import com.getir.data.api.ServiceInterface
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ServiceRepositoryImpl @Inject constructor(
    private val serviceInterface: ServiceInterface
) : ServiceRepository {

    override suspend fun getProducts(): Single<List<DemoResponse>> {
        return serviceInterface.fetchProduct()
    }
}
