package com.example.Data

import com.example.Pojo.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface Products_Interface {

    @GET("/xml/?action=getproducts&uiq={uiq}&companyid={companyid}")
    fun get_products(@Path(value = "uiq") uiq:String, @Path(value = "companyid") companyid:Int):List<Product>
}