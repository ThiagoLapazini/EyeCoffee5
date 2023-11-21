package com.example.eyecoffee.dao

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Produto::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun ProdutoDAO(): ProdutoDAO

    companion object {

        private const val DATABASE_NAME: String = "eyedb"

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it
                    Log.d("AppDatabase", "Banco de dados criado")
                }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java, DATABASE_NAME
            ).build()
    }
}

