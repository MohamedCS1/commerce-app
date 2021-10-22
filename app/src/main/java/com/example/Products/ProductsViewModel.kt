package com.example.Products

import android.app.AlertDialog
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Pojo.Product
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import androidx.lifecycle.LiveData




class ProductsViewModel():ViewModel() {

    val MutableLiveDataProducts = MutableLiveData<ArrayList<Product>>()

    val array_products = arrayListOf<Product>()

    fun getProducts(uiq:String, company_id:String, progress: ProgressBar?, tv_loading: TextView?)
    {
        progress?.visibility = View.VISIBLE
        tv_loading?.visibility = View.VISIBLE
        val t = Thread(Runnable {
            fun sendGet() {
                val url = URL("https://app.mytasks.click/xml/?action=getproducts&uiq=$uiq&companyid=$company_id")
                try {

                    with(url.openConnection() as HttpURLConnection) {
                        requestMethod = "GET"

                        inputStream.bufferedReader().use {

                            var temptext:String = ""
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                var index = 0

                                it.lines().forEach { line ->
                                    index++
                                    if (index == 8)
                                    {
                                        temptext = line
                                    }
                                }

                            }
                            val arraysplit = temptext.split("<" ,">")

                            var product_id:String? = null
                            var product_title:String? = null
                            var product_description:String? = null
                            var product_barcode:String? = null
                            var product_price:String? = null
                            var product_image:String? = null
                            var product_lastversion:String? = null

                            val finalarray = arrayListOf<String>()

                            var f = 0
                            for (i in arraysplit)
                            {
                                if (i == "PRODUCT" && f != 0)
                                {
                                    finalarray.add(" ")
                                    continue
                                }

                                if (i == "" || i == "  " || i == "   " || i == "PRODUCT" || i == "data" || i == "/data" || i == "/PRODUCT"  || i == "product_ID" || i == "/product_ID" || i == "product_title" || i == "/product_title" || i == "product_description" || i == "/product_description" || i == "product_BARCODE"|| i == "/product_BARCODE" || i == "product_PRICE" || i == "/product_PRICE" || i == "lastversion" || i == "/lastversion" || i == "main_image" || i == "/main_image" || i == "main_image/")
                                {

                                    continue
                                }

                                if (i[0].toString() == "/")
                                {

                                    finalarray.add(i)
                                    continue
                                }
                                finalarray.add(i)
                                f++
                            }

                            fun insert()
                            {
                                if (product_image != " ")
                                {
                                    array_products.add(Product(null ,product_id.toString() ,product_title.toString() ,product_description.toString() ,product_barcode.toString() ,product_price.toString() ,product_lastversion.toString() ,product_image.toString()))
                                    product_image = ""

                                }
                                else
                                {
                                    array_products.add(Product(null ,product_id.toString() ,product_title.toString() ,product_description.toString() ,product_barcode.toString() ,product_price.toString() ,product_lastversion.toString() ,""))
                                }
                            }


                            var c = 0
                            for (i in finalarray)
                            {

                                if (c == 0)
                                {
                                    product_id = i
                                    c++
                                    continue
                                }
                                else if (c == 1)
                                {
                                    product_title = i
                                    c++
                                    continue
                                }
                                else if (c == 2)
                                {
                                    product_description = i
                                    c++
                                    continue
                                }
                                else if (c == 3)
                                {
                                    product_barcode = i
                                    c++
                                    continue
                                }
                                else if (c == 4)
                                {
                                    product_price = i
                                    c++
                                    continue
                                }
                                else if (c == 5)
                                {
                                    product_lastversion = i
                                    c++
                                    continue
                                }
                                else if (c == 6 && i[0].toString() == "/")
                                {
                                    product_image = i
                                    continue
                                }
                                else if (i == " ")
                                {
                                    if (product_image != " ")
                                    {
                                        insert()
                                        c = 0
                                        product_image = ""
                                        continue
                                    }
                                    else
                                    {
                                        insert()
                                        c = 0
                                        continue
                                    }
                                }

                            }
                            insert()
                            MutableLiveDataProducts.postValue(array_products)
                        }

                    }

                }catch (ex: Exception)
                {
                }


            }
            sendGet()
        })
        t.start()

    }

}