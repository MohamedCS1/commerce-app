package com.example.Products

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Data.PrefManage
import com.example.Pojo.Product
import com.example.phons.R

class Products_activity : AppCompatActivity() {

    var productsViewModel:ProductsViewModel? = null
    var adapter:ProductsAdapter? = null
    var rv_product:RecyclerView? = null
    var progress:ProgressBar? = null
    var tv_loading:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        val prf = PrefManage()
        prf.prefcreate(this)

        val uiq = prf.getuiq()

        val compid = prf.getcompid()

        progress = findViewById(R.id.progressBar_product)

        tv_loading = findViewById(R.id.tv_loadin_product)

        productsViewModel = ProductsViewModel()

        productsViewModel!!.getProducts(uiq ,compid ,progress!! ,tv_loading!!)

        adapter = ProductsAdapter(this)

        val lm = GridLayoutManager(this ,2)

        rv_product = findViewById(R.id.rv_product)

        rv_product!!.adapter = adapter

        rv_product!!.layoutManager = lm

        lm.isSmoothScrolling

//        productsViewModel!!.MutableLiveDataProducts.observe(this ,object :Observer<ArrayList<Product>>
//        {
//            @SuppressLint("NotifyDataSetChanged")
//            override fun onChanged(t: ArrayList<Product>?) {
//                Log.d("tt",t?.size.toString())
//                progress!!.visibility = View.GONE
//                tv_loading!!.visibility = View.GONE
//                adapter!!.setList(t!!)
//            }
//
//        })
    }


}