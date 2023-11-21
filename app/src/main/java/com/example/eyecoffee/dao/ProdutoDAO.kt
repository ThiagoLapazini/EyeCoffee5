package com.example.eyecoffee.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProdutoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(produto: List<Produto>)

    @Query("SELECT * FROM produtos_table")
    suspend fun getAllProdutos(): List<Produto>
}
