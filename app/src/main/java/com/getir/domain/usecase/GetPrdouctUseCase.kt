package com.getir.domain.usecase

import com.getir.data.repository.ServiceRepositoryImpl
import javax.inject.Inject

class GetPrdouctUseCase @Inject constructor(private val serviceRepositoryImpl: ServiceRepositoryImpl) {
    suspend operator fun invoke(id:String)= serviceRepositoryImpl.getLocalItem(id)
}