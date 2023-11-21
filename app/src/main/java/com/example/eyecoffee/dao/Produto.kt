package com.example.eyecoffee.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "produtos_table")
data class Produto (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val productTitle: String,
    val productPrice: String,
    val productImage: String
)

