package com.getir.domain.repository

import com.getir.data.remote.Product
import com.getir.data.remote.ProductResponse
import com.getir.data.remote.SuggestedProductResponse
import com.getir.core.Response
import com.getir.data.local.ItemEntity
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {
    suspend fun getProducts(): Flow<Response<List<ProductResponse>>>
    suspend fun getSuggestedProducts(): Flow<Response<List<SuggestedProductResponse>>>
    suspend fun getTotalPrice():Flow<Double?>
    suspend fun insertDataBaseItem(item: Product)
    suspend fun getLocalItems():Flow<List<ItemEntity>>
    suspend fun getLocalItem(id:String): ItemEntity?
    suspend fun deleteAllItems()
}