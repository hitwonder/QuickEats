package com.app.kiosk.dao


import androidx.room.*
import com.app.kiosk.model.CartItem

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem)

    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): LiveData<List<CartItem>>

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Query("SELECT SUM(price * quantity) FROM cart_items")
    fun getTotalPrice(): LiveData<Double>

    @Query("SELECT * FROM cart_items WHERE name = :itemName LIMIT 1")
    suspend fun getCartItemByName(itemName: String): CartItem?
}
