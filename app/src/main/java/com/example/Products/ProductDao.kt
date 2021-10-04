package com.example.Products

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.Pojo.Product

@Dao
interface ProductDao {
    @Insert
    fun insertProduct(product:Product)

    @Query("select * from product_table")
    fun getproduct():List<Product>

}