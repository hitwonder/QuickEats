package com.app.kiosk.repository

import androidx.lifecycle.LiveData
import com.app.kiosk.dao.CartDao
import com.app.kiosk.model.CartItem
import javax.inject.Inject

import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(private val cartDao: CartDao) {

    val cartItems: LiveData<List<CartItem>> = cartDao.getAllCartItems()
    val totalPrice: LiveData<Double> = cartDao.getTotalPrice()

    suspend fun addToCart(cartItem: CartItem) {
        // Check if the item already exists in the cart
        val existingItem = cartDao.getCartItemByName(cartItem.name)

        if (existingItem != null) {
            // If it exists, update the quantity
            existingItem.quantity += cartItem.quantity
            cartDao.insertCartItem(existingItem) // Update in the database
        } else {
            // If it doesn't exist, insert the new cart item
            cartDao.insertCartItem(cartItem)
        }
    }

    suspend fun removeFromCart(cartItem: CartItem) {
        cartDao.deleteCartItem(cartItem)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }
}

