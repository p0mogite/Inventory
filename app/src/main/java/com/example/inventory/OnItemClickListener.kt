package com.example.inventory

import com.example.inventory.data.Item

interface OnItemClickListener {
    fun onItemClick(item: Item)
}