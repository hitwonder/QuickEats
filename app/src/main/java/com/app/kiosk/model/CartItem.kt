package com.app.kiosk.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Default value set to 0
    val name: String,
    val description : String,
    val imageUrl : String,
    val price: Double,
    var quantity: Int
)
