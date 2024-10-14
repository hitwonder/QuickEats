package com.app.kiosk.viewmodel


import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.kiosk.model.Item
import com.app.kiosk.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ItemViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {

    private val _itemsLiveData = MutableLiveData<List<Item>>()
    val itemsLiveData: LiveData<List<Item>> get() = _itemsLiveData

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            val items = itemRepository.loadItemsFromJson()
            _itemsLiveData.postValue(items ?: emptyList())
        }
    }
}