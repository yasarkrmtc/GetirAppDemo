package com.getir.domain.usecase

import com.getir.data.remote.Product
import com.getir.data.repository.ServiceRepositoryImpl
import javax.inject.Inject

class InsertDataBaseUseCase @Inject constructor(private val serviceRepositoryImpl: ServiceRepositoryImpl) {
    suspend operator fun invoke(item: Product)= serviceRepositoryImpl.insertDataBaseItem(item)
}