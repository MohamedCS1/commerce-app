package com.example.Products

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        val prf = PrefManage()
        prf.prefcreate(this)

        val uiq = prf.getuiq()

        val compid = prf.getcompid()

        productsViewModel = ProductsViewModel()

        productsViewModel!!.getProducts(uiq ,compid)

        adapter = ProductsAdapter(this)

        val lm = GridLayoutManager(this ,2)

        rv_product = findViewById(R.id.rv_product)

        rv_product!!.adapter = adapter

        rv_product!!.layoutManager = lm

        lm.isSmoothScrolling

        productsViewModel!!.MutableLiveDataProducts.observe(this ,object :Observer<ArrayList<Product>>
        {
            @SuppressLint("NotifyDataSetChanged")
            override fun onChanged(t: ArrayList<Product>?) {
                Log.d("tt",t?.size.toString())
                adapter!!.setList(t!!)
            }

        })
    }


}