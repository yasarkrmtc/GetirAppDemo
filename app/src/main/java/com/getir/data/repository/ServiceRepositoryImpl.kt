package com.getir.data.repository

import com.getir.data.api.DemoResponse
import com.getir.data.api.ServiceInterface
import com.getir.model.Response
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject

class ServiceRepositoryImpl @Inject constructor(
    private val serviceInterface: ServiceInterface
) : ServiceRepository {

    override suspend fun getProducts(): Flow<Response<List<DemoResponse>>> {
        return callbackFlow {
            val call = serviceInterface.fetchProduct()
            trySend(Response.Loading)
            call.enqueue(object : Callback<List<DemoResponse>> {
                override fun onResponse(
                    call: Call<List<DemoResponse>>,
                    response: retrofit2.Response<List<DemoResponse>>
                ) {
                    val itemResponses = response.body() ?: emptyList()
                    trySend(Response.Success(itemResponses))
                }

                override fun onFailure(call: Call<List<DemoResponse>>, t: Throwable) {
                    trySend(
                        Response.Error(
                            _code = t.hashCode().toString(),
                            _message = t.localizedMessage
                        )
                    )
                }

            })
            awaitClose()
        }
    }
}
