package com.example.Pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Product_table")
data class Product(@PrimaryKey(autoGenerate = true) var unique_Id: Int?, var id:String, var title:String, var description:String, var barcode:String, var price:String, var lastversion:String, var image:String)
