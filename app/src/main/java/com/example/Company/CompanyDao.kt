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

    @Query("select unique_Id from company_table")
    fun getallid():List<Int>

    @Query("delete from company_table WHERE unique_Id = :uniq_id")
    fun deleteByUserId(uniq_id: Long)

    @Query("select * from company_table")
    fun getallcompany():List<Companies>
}