package com.example.inventory.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDao {
    @Query("SELECT * FROM item_table")
    suspend fun get(): Item?

    @Query("SELECT * FROM item_table")
    fun getAllItems(): LiveData<List<Item>>

    @Query("SELECT * FROM item_table")
    suspend fun getItem(): Item

    @Query("DELETE FROM item_table")
    suspend fun deleteAll()

    @Insert
    suspend fun insert(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Update
    suspend fun updateItem(vararg item: Item)
}