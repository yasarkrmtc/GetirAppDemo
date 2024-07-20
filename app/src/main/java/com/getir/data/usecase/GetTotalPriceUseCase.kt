package com.getir.data.usecase

import com.getir.data.repository.ServiceRepositoryImpl
import javax.inject.Inject

class GetTotalPriceUseCase @Inject constructor(private val serviceRepositoryImpl: ServiceRepositoryImpl) {
    suspend operator fun invoke()= serviceRepositoryImpl.getTotalPrice()
}