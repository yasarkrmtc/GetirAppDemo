package com.getir.data.repository

import com.getir.data.api.DemoResponse
import com.getir.model.Response
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {
    suspend fun getProducts(): Flow<Response<List<DemoResponse>>>

}