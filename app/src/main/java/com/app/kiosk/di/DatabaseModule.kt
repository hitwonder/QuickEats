package com.app.kiosk.di

import android.content.Context
import androidx.room.Room
import com.app.kiosk.dao.CartDao
import com.app.kiosk.db.CartDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCartDatabase(@ApplicationContext context: Context): CartDatabase {
        return Room.databaseBuilder(
            context,
            CartDatabase::class.java,
            "cart_database"
        ).build()
    }

    @Provides
    fun provideCartDao(cartDatabase: CartDatabase): CartDao {
        return cartDatabase.cartDao()
    }
}
