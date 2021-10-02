package com.example.Company

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Data.PrefManage
import com.example.Pojo.Companies
import com.example.Settings.Settings
import com.example.phons.R


class Company_activity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")

    var CompanyViewModel:CompanyViewModel? = null
    var rv_company:RecyclerView? = null
    var adapter:CompanyAdapter? = null
    var progress:ProgressBar? = null
    var tv_loading:TextView? = null
    var bu_go_to_settengs:CardView? = null
    var et_searchbar:EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)


        val prf = PrefManage()
        prf.prefcreate(this)

        val uiq = prf.getuiq()

        progress = findViewById(R.id.my_progressBar)

        tv_loading = findViewById(R.id.tv_loading)

        et_searchbar = findViewById(R.id.et_searchbar)

        et_searchbar!!.isEnabled = false

        CompanyViewModel = CompanyViewModel()

        CompanyViewModel!!.getcompany(uiq)

        bu_go_to_settengs = findViewById(R.id.bu_gotoseting)

        bu_go_to_settengs!!.setOnClickListener {
            startActivity(Intent(this ,Settings::class.java))
        }

        adapter = CompanyAdapter(this)

        val lm = GridLayoutManager(this, 2)

        rv_company = findViewById(R.id.rv_company)

        rv_company!!.adapter = adapter

        rv_company!!.layoutManager = lm


        lm.isSmoothScrolling

        CompanyViewModel!!.MutableLiveDataCompaby.observe(this ,object : Observer<ArrayList<Companies>>{
            override fun onChanged(t: ArrayList<Companies>?) {
                et_searchbar!!.isEnabled = true
                progress!!.visibility = View.GONE
                tv_loading!!.visibility = View.GONE
                adapter!!.setList(t!!)
            }

        })


        adapter!!.onclickproduct(object :OnclicCompanyInterface{
            override fun onclickcompany(companiinfo: Companies) {
                prf.insertuiq(uiq)
                prf.insertcompid(companiinfo.compid)
            }

        })

    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}