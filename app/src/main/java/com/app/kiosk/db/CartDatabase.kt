package com.app.kiosk.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.kiosk.dao.CartDao
import com.app.kiosk.model.CartItem

@Database(entities = [CartItem::class], version = 1)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}
