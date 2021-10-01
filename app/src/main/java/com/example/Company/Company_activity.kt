package com.example.Company

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Data.PrefManage
import com.example.Pojo.Companies
import com.example.phons.R

class Company_activity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")

    var CompanyViewModel:CompanyViewModel? = null
    var rv_company:RecyclerView? = null
    var adapter:CompanyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)


        val prf = PrefManage()
        prf.prefcreate(this)

        val uiq = prf.getuiq()

        CompanyViewModel = CompanyViewModel()

        CompanyViewModel!!.getcompany(uiq)

        adapter = CompanyAdapter(this)

        val lm = GridLayoutManager(this, 2)

        rv_company = findViewById(R.id.rv_company)

        rv_company!!.adapter = adapter

        rv_company!!.layoutManager = lm


        lm.isSmoothScrolling

        CompanyViewModel!!.MutableLiveDataCompaby.observe(this ,object : Observer<ArrayList<Companies>>{
            override fun onChanged(t: ArrayList<Companies>?) {
                adapter!!.setList(t!!)
            }

        })

    }
}