package com.getir.data.usecase

import com.getir.data.api.SuggestedProductResponse
import com.getir.data.repository.ServiceRepositoryImpl
import com.getir.model.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchSuggestedProductUseCase @Inject constructor(
    private val repositoryImpl: ServiceRepositoryImpl
) {
    suspend operator fun invoke() : Flow<Response<List<SuggestedProductResponse>>> = repositoryImpl.getSuggestedProducts()
}