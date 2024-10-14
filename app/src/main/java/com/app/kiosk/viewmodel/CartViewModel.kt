package com.app.kiosk.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.kiosk.model.CartItem
import com.app.kiosk.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

import androidx.lifecycle.*

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: CartRepository) : ViewModel() {

    val cartItems: LiveData<List<CartItem>> = repository.cartItems
    val totalPrice: LiveData<Double> = repository.totalPrice

    fun addToCart(cartItem: CartItem) {
        viewModelScope.launch {
            repository.addToCart(cartItem)
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            repository.removeFromCart(cartItem)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }
}

