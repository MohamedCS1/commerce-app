package com.example.Company

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Data.CompanyDatabase
import com.example.Data.PrefManage
import com.example.Pojo.Companies
import com.example.Settings.Settings
import com.example.phons.R


class Company_activity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")

    var rv_company:RecyclerView? = null
    var adapter:CompanyAdapter? = null
    var bu_go_to_settengs:CardView? = null
    var et_searchbar:EditText? = null

    var companydatabase: CompanyDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)
        companydatabase = CompanyDatabase.getInstance(this)

        val arr1 = arrayListOf<Companies>()
        Thread(Runnable {
                val arr = companydatabase?.CompanyDao()?.getallcompany()

                for (i in arr!!)
                {
                    if (i.image_link!= " ")
                    {

                        arr1.add(Companies(null,i.compid.toString() ,i.company_title.toString() ,i.lastversion.toString() ,i.image_link.toString(),null))


                    }
                    else
                    {
                        arr1.add(Companies(null,i.compid.toString() ,i.company_title.toString() ,i.lastversion.toString(),"" ,null))
                    }

                }
            }
        ).start()


        et_searchbar = findViewById(R.id.et_searchbar)

        et_searchbar!!.isEnabled = false

        bu_go_to_settengs = findViewById(R.id.bu_gotoseting)

        bu_go_to_settengs!!.setOnClickListener {
            startActivity(Intent(this ,Settings::class.java))
        }

        adapter = CompanyAdapter(this)

        val lm = GridLayoutManager(this, 2)

        rv_company = findViewById(R.id.rv_company)

        rv_company!!.adapter = adapter

        rv_company!!.layoutManager = lm


        adapter!!.setList(arr1)
        lm.isSmoothScrolling


    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}