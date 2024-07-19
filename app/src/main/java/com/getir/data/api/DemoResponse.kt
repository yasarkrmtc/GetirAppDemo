package com.getir.data.api

import com.google.gson.annotations.SerializedName

data class DemoResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String?,
    @SerializedName("productCount") val productCount: Int?,
    @SerializedName("products") val products: List<Product>?
)

data class Product(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String?,
    @SerializedName("attribute") val attribute: String?,
    @SerializedName("thumbnailURL") val thumbnailURL: String?,
    @SerializedName("imageURL") val imageURL: String?,
    @SerializedName("price") val price: Double?,
    @SerializedName("priceText") val priceText: String?,
    @SerializedName("shortDescription") val shortDescription: String?
)

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String?,
    @SerializedName("password") val password: String?
)
