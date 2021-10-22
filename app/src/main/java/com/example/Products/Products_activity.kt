package com.example.Products

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Pair
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Data.ProductDatabase
import com.example.Pojo.Companies
import com.example.Pojo.Product
import com.example.phons.R
import java.util.*
import kotlin.collections.ArrayList

class Products_activity : AppCompatActivity() {


    var adapter:ProductsAdapter? = null
    var rv_product:RecyclerView? = null
    var productdatabase: ProductDatabase? = null
    var et_search_product:EditText? = null
    var bu_clear: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        productdatabase = ProductDatabase.getInstance(this)

        et_search_product = findViewById(R.id.et_searchbar_product)

        bu_clear = findViewById(R.id.bu_clear_product)
        var k = listOf<Product>()
        Thread(Runnable {
            val arr = productdatabase?.ProductDao()?.getallproduct()!!
         k  =  arr.distinctBy { Pair(it.barcode ,it.barcode) }

            adapter!!.setList(k as ArrayList<Product>)

        }
        ).start()

        et_search_product?.addTextChangedListener(object :TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s == null) return
                filter(s.toString() ,k as ArrayList<Product>)
            }

        })

        bu_clear!!.setOnClickListener {

            et_search_product?.setText("")
        }

        adapter = ProductsAdapter(this)

        val lm = GridLayoutManager(this ,2)

        rv_product = findViewById(R.id.rv_product)

        rv_product!!.adapter = adapter


        rv_product!!.layoutManager = lm

        lm.isSmoothScrolling

    }
    fun filter(text: String, Array: ArrayList<Product>)
    {
        val listfilter = arrayListOf<Product>()

        for (p in Array)
        {
            if (p.title.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault())))
            {
                listfilter.add(p)
            }
        }
        adapter!!.setList(listfilter)

    }


}