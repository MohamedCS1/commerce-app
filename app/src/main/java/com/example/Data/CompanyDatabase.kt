package com.example.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.Company.CompanyDao
import com.example.Pojo.Companies

@Database(version = 7 ,entities = [Companies::class])
abstract class CompanyDatabase : RoomDatabase() {
    abstract fun CompanyDao(): CompanyDao?

    companion object {
        private var instance: CompanyDatabase? = null
        @Synchronized fun getInstance(context: Context): CompanyDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, CompanyDatabase::class.java, "company_database")
                    .build()
            }
            return instance
        }
    }
}