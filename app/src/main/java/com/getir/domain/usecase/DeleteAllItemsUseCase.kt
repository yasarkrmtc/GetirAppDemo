package com.getir.domain.usecase


import com.getir.data.repository.ServiceRepositoryImpl
import javax.inject.Inject

class DeleteAllItemsUseCase @Inject constructor(private val serviceRepositoryImpl: ServiceRepositoryImpl) {
    suspend operator fun invoke() = serviceRepositoryImpl.deleteAllItems()
}
