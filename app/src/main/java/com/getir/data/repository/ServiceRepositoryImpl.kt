package com.getir.data.repository

import com.getir.data.remote.Product
import com.getir.data.remote.ProductResponse
import com.getir.data.remote.ServiceInterface
import com.getir.data.remote.SuggestedProductResponse
import com.getir.core.Response
import com.getir.data.local.CartDAO
import com.getir.data.local.ItemEntity
import com.getir.domain.repository.ServiceRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject

class ServiceRepositoryImpl @Inject constructor(
    private val serviceInterface: ServiceInterface, private val cartDAO: CartDAO
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
                            _code = t.hashCode().toString(), _message = t.localizedMessage
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
                            _code = t.hashCode().toString(), _message = t.localizedMessage
                        )
                    )
                }

            })
            awaitClose()
        }
    }

    override suspend fun getTotalPrice(): Flow<Double?> = cartDAO.getTotalPrice()

    override suspend fun getLocalItems(): Flow<List<ItemEntity>> {
        return cartDAO.getAllItems()
    }

    override suspend fun getLocalItem(id: String): ItemEntity? {
        return cartDAO.getItemById(id)
    }
    override suspend fun deleteAllItems() {
        return cartDAO.deleteAll()
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
                    totalOrder = 1,
                    squareThumbnailURL = item.squareThumbnailURL,
                    category = item.category,
                    status = item.status,
                    unitPrice = item.unitPrice
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
                        totalOrder = item.totalOrder,
                        squareThumbnailURL = item.squareThumbnailURL,
                        category = item.category,
                        status = item.status,
                        unitPrice = item.unitPrice
                    )
                )
            }
        }

    }

}
