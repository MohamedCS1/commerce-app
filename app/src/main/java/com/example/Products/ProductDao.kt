package com.example.Products

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.Pojo.Companies
import com.example.Pojo.Product

@Dao
interface ProductDao {
    @Insert
    fun insertProduct(product:Product)

    @Query("select * from product_table")
    fun getproduct():List<Product>

    @Query("select unique_Id from product_table")
    fun getallid():List<Int>

    @Query("delete from product_table WHERE unique_Id = :uniq_id")
    fun deleteByUserId(uniq_id: Long)

    @Query("select * from product_table")
    fun getallproduct():List<Product>
}