package com.example.inventory.data

import androidx.lifecycle.LiveData

class ItemRepository(private val dao: ItemDao) {
    val allItems: LiveData<List<Item>> = dao.getAllItems()

    suspend fun insert(item: Item) {
        dao.insert(item)
    }

    suspend fun delete(item: Item) {
        dao.delete(item)
    }

    suspend fun update(item: Item) {
        dao.updateItem(item)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}