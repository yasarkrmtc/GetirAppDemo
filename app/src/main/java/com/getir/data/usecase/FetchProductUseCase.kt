package com.getir.data.usecase

import com.getir.data.api.DemoResponse
import com.getir.data.repository.ServiceRepositoryImpl
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FetchProductUseCase @Inject constructor(
    private val repositoryImpl: ServiceRepositoryImpl
) {
    suspend operator fun invoke() : Single<DemoResponse> = repositoryImpl.getFoods()

}