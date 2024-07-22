package com.getir.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String?,
    val attribute: String?,
    val thumbnailURL: String?,
    val squareThumbnailURL: String?,
    val imageURL: String?,
    val price: Double?,
    val priceText: String?,
    val shortDescription: String?,
    val category: String?,
    val unitPrice: Double?,
    val status: Int?,
    var totalOrder: Int = 0, )
