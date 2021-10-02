package com.example.Pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company_table")
data class Companies(@PrimaryKey(autoGenerate = true) var unique_Id:Int?, var compid:String?, var company_title:String?, var lastversion:String?, var image_link:String?, var image: String?)
