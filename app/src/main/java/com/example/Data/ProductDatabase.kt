package com.example.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.Pojo.Product
import com.example.Products.ProductDao


@Database(entities = [Product::class] ,version = 55)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun ProductDao(): ProductDao?

    companion object {
        private var instance: ProductDatabase? = null
        @Synchronized fun getInstance(context: Context): ProductDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, ProductDatabase::class.java, "product_database")
                    .build()
            }
            return instance
        }
    }
}