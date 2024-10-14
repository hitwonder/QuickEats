package com.app.kiosk.util

import android.content.Context
import android.widget.Toast

object Utils {

    fun calculateTotalPrice(price: Double, quantity: Int): Double {
        return price * quantity
    }

    fun showShortToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}