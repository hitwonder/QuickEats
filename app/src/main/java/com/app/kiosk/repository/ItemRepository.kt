package com.app.kiosk.repository


import android.content.Context
import com.app.kiosk.model.Item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    // Load items asynchronously using coroutines
    suspend fun loadItemsFromJson(): List<Item>? = withContext(Dispatchers.IO) {
        try {
            val json: String =
                context.assets.open("items.json").bufferedReader().use { it.readText() }

            // Parse JSON using Gson
            val gson = Gson()
            val itemListType: Type = object : TypeToken<List<Item>>() {}.type

            // Convert JSON string to List of Item objects
            return@withContext gson.fromJson<List<Item>>(json, itemListType)
        } catch (ex: Exception) {
            ex.printStackTrace()
            return@withContext null
        }
    }


}
