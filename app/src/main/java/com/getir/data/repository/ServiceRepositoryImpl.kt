package com.getir.data.repository

import com.getir.data.api.Product
import com.getir.data.api.ProductResponse
import com.getir.data.api.ServiceInterface
import com.getir.data.api.SuggestedProductResponse
import com.getir.model.Response
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject

class ServiceRepositoryImpl @Inject constructor(
    private val serviceInterface: ServiceInterface,
    private val cartDAO: CartDAO
) : ServiceRepository {

    override suspend fun getProducts(): Flow<Response<List<ProductResponse>>> {
        return callbackFlow {
            val call = serviceInterface.fetchProduct()
            trySend(Response.Loading)
            call.enqueue(object : Callback<List<ProductResponse>> {
                override fun onResponse(
                    call: Call<List<ProductResponse>>,
                    response: retrofit2.Response<List<ProductResponse>>
                ) {
                    val itemResponses = response.body() ?: emptyList()
                    trySend(Response.Success(itemResponses))
                }

                override fun onFailure(call: Call<List<ProductResponse>>, t: Throwable) {
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

    override suspend fun getSuggestedProducts(): Flow<Response<List<SuggestedProductResponse>>> {
        return callbackFlow {
            val call = serviceInterface.fetchSuggestedProduct()
            trySend(Response.Loading)
            call.enqueue(object : Callback<List<SuggestedProductResponse>> {
                override fun onResponse(
                    call: Call<List<SuggestedProductResponse>>,
                    response: retrofit2.Response<List<SuggestedProductResponse>>
                ) {
                    val itemResponses = response.body() ?: emptyList()
                    trySend(Response.Success(itemResponses))
                }

                override fun onFailure(call: Call<List<SuggestedProductResponse>>, t: Throwable) {
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

    override suspend fun insertDataBaseItem(item: Product) {
        if (item.totalOrder == 0) {
            cartDAO.delete(
                ItemEntity(
                    id = item.id,
                    name = item.name,
                    attribute = item.attribute,
                    thumbnailURL = item.thumbnailURL,
                    imageURL = item.imageURL,
                    price = item.price,
                    priceText = item.priceText,
                    shortDescription = item.shortDescription,
                    totalOrder = 1
                )
            )

        } else {
            val existingItem = cartDAO.getItemById(item.id)
            if (existingItem != null) {
                existingItem.totalOrder = item.totalOrder
                cartDAO.update(existingItem)
            } else {
                cartDAO.insert(
                    ItemEntity(
                        id = item.id,
                        name = item.name,
                        attribute = item.attribute,
                        thumbnailURL = item.thumbnailURL,
                        imageURL = item.imageURL,
                        price = item.price,
                        priceText = item.priceText,
                        shortDescription = item.shortDescription,
                        totalOrder = item.totalOrder
                    )
                )
            }
        }

    }
}
