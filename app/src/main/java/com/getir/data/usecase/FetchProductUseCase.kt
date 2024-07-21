package com.getir.data.usecase

import com.getir.data.api.ProductResponse
import com.getir.data.repository.ServiceRepositoryImpl
import com.getir.model.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchProductUseCase @Inject constructor(
    private val repositoryImpl: ServiceRepositoryImpl
) {
    suspend operator fun invoke() : Flow<Response<List<ProductResponse>>> = repositoryImpl.getProducts()
}