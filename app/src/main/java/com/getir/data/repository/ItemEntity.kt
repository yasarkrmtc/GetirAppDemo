package com.getir.data.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String?,
    val attribute: String?,
    val thumbnailURL: String?,
    val imageURL: String?,
    val price: Double?,
    val priceText: String?,
    val shortDescription: String?,
    var totalOrder: Int = 0, )