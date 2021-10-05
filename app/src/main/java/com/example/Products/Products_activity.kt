package com.example.Products

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Data.ProductDatabase
import com.example.Pojo.Product
import com.example.phons.R

class Products_activity : AppCompatActivity() {


    var adapter:ProductsAdapter? = null
    var rv_product:RecyclerView? = null
    var productdatabase: ProductDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        productdatabase = ProductDatabase.getInstance(this)

        Thread(Runnable {
            val arr = productdatabase?.ProductDao()?.getallproduct()!!
          val k =  arr.distinctBy { Pair(it.barcode ,it.barcode) }

            adapter!!.setList(k as ArrayList<Product>)

//           val arr2 = productdatabase?.ProductDao()?.getallproduct()!!.toTypedArray()

//            val arr3 = arrayListOf<Product>()
//            for (i in arr)
//            {
//                for (j in arr2)
//                {
//                    if (i.lastversion != i.lastversion)
//                    {
//
//
//
//                    }
//                }
//            }
//            for (i in arr3) print(i)

//            arr2.add(arr)

//            for (i in arr)
//            {
//                if (i.image != " ")
//                {
//                    arr1.add(Product(null ,i.id.toString() ,i.title.toString() ,i.description.toString() ,i.barcode.toString() ,i.price.toString() ,i.lastversion.toString() ,i.image.toString()))
//
//                }
//                else
//                {
//                    arr1.add(Product(null ,i.id.toString()  ,i.title.toString() ,i.description.toString() ,i.barcode.toString() ,i.price.toString() ,i.lastversion.toString() ,""))
//                }
//
//            }
        }
        ).start()

        adapter = ProductsAdapter(this)

        val lm = GridLayoutManager(this ,2)

        rv_product = findViewById(R.id.rv_product)

        rv_product!!.adapter = adapter

        val arr1 = arrayListOf<Product>()


        rv_product!!.layoutManager = lm

        lm.isSmoothScrolling

    }


}