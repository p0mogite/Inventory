package com.example.inventory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDatabase
import com.example.inventory.data.ItemRepository
import kotlinx.coroutines.launch

class ActivityViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ItemRepository
    val allItems: LiveData<List<Item>>

    init {
        val dao = ItemDatabase.getDatabase(application).itemDao()
        repository = ItemRepository(dao)
        allItems = repository.allItems
    }

    fun insert(item: Item) {
        viewModelScope.launch {
            repository.insert(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            repository.delete(item)
        }
    }

    fun update(item: Item) {
        viewModelScope.launch {
            repository.update(item)
        }
    }

    fun delete() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}