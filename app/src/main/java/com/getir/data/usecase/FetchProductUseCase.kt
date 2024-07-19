package com.getir.data.usecase

import com.getir.data.api.DemoResponse
import com.getir.data.repository.ServiceRepositoryImpl
import com.getir.model.Response
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchProductUseCase @Inject constructor(
    private val repositoryImpl: ServiceRepositoryImpl
) {
    suspend operator fun invoke() : Flow<Response<List<DemoResponse>>> = repositoryImpl.getProducts()

}