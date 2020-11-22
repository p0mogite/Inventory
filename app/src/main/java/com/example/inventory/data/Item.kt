package com.example.inventory.data

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
data class Item(
    @PrimaryKey
    @ColumnInfo(name = "imageUri") val imageUri: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "supplier") val supplier: String,
    @ColumnInfo(name = "quantity") val quantity: String,
    @ColumnInfo(name = "price") val price: String
)