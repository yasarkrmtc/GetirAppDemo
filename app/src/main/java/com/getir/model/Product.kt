package com.getir.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: String,
    @SerializedName("imageURL") val imageUrl: String?,
    @SerializedName("price") val price: Double,
    @SerializedName("name") val name: String,
    @SerializedName("priceText") val priceText: String,
    @SerializedName("shortDescription") val shortDescription: String?,
    @SerializedName("category") val category: String?,
    @SerializedName("unitPrice") val unitPrice: Double?,
    @SerializedName("squareThumbnailURL") val squareThumbnailUrl: String?,
    @SerializedName("status") val status: Int?
)