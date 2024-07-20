package com.getir.data.repository

import com.getir.data.api.Product
import com.getir.data.api.ProductResponse
import com.getir.data.api.SuggestedProductResponse
import com.getir.model.Response
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {
    suspend fun getProducts(): Flow<Response<List<ProductResponse>>>
    suspend fun getSuggestedProducts(): Flow<Response<List<SuggestedProductResponse>>>

    suspend fun getTotalPrice():Flow<Double?>
    suspend fun insertDataBaseItem(item: Product)
    suspend fun getLocalItems():Flow<List<ItemEntity>>


}