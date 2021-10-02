package com.example.Company

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.Pojo.Companies
import com.example.Pojo.Product


@Dao
interface CompanyDao {
    @Insert
    fun insertCompany(company:Companies)

    @Query("select * from company_table")
    fun getcompanys():List<Companies>

    @Query("delete from company_table")
    fun claer_db()
}