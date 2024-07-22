package com.getir.domain.usecase

import com.getir.data.remote.ProductResponse
import com.getir.data.repository.ServiceRepositoryImpl
import com.getir.core.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchProductUseCase @Inject constructor(
    private val repositoryImpl: ServiceRepositoryImpl
) {
    suspend operator fun invoke() : Flow<Response<List<ProductResponse>>> = repositoryImpl.getProducts()
}