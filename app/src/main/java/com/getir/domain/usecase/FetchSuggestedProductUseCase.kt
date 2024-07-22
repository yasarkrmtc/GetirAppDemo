package com.getir.domain.usecase

import com.getir.data.remote.SuggestedProductResponse
import com.getir.data.repository.ServiceRepositoryImpl
import com.getir.core.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchSuggestedProductUseCase @Inject constructor(
    private val repositoryImpl: ServiceRepositoryImpl
) {
    suspend operator fun invoke() : Flow<Response<List<SuggestedProductResponse>>> = repositoryImpl.getSuggestedProducts()
}