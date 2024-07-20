package com.getir.data.api

import com.google.gson.annotations.SerializedName

data class ProductResponse(
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
    @SerializedName("shortDescription") val shortDescription: String?,
    var totalOrder:Int=0,
)

data class SuggestedProduct(
    @SerializedName("id") val id: String,
    @SerializedName("imageURL") val imageURL: String?,
    @SerializedName("squareThumbnailURL") val squareThumbnailURL: String?,
    @SerializedName("price") val price: Double,
    @SerializedName("name") val name: String,
    @SerializedName("priceText") val priceText: String,
    @SerializedName("shortDescription") val shortDescription: String?,
    @SerializedName("category") val category: String?,
    @SerializedName("unitPrice") val unitPrice: Double?,
    @SerializedName("status") val status: Int?,
    var totalOrder:Int=0,
)

data class SuggestedProductResponse(
    @SerializedName("products") val products: List<SuggestedProduct>?,

)



