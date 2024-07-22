package com.getir.data.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ProductResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String?,
    @SerializedName("productCount") val productCount: Int?,
    @SerializedName("products") var products: List<Product>?
)

@Parcelize
data class Product(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String?,
    @SerializedName("attribute") val attribute: String?,
    @SerializedName("squareThumbnailURL") val squareThumbnailURL: String?,
    @SerializedName("thumbnailURL") val thumbnailURL: String?,
    @SerializedName("imageURL") val imageURL: String?,
    @SerializedName("price") val price: Double?,
    @SerializedName("priceText") val priceText: String?,
    @SerializedName("category") val category: String?,
    @SerializedName("unitPrice") val unitPrice: Double?,
    @SerializedName("status") val status: Int?,
    @SerializedName("shortDescription") val shortDescription: String?,
    var totalOrder: Int = 0
) : Parcelable


data class SuggestedProductResponse(
    @SerializedName("products") var products: List<Product>?,
)