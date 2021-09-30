package com.example.Data

import com.example.Pojo.Companies
import com.example.Pojo.Login
import com.example.Pojo.Product
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class Client_Data {

    var baseUrl:String = "https://app.mytasks.click"
    var companiesinterface:Compani_Interface? = null
    var productsinterface:Products_Interface? = null
    var logininterface:Login_Interface? = null
    var INSTANCE:Client_Data? = null

    constructor()
    {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        companiesinterface = retrofit.create(Compani_Interface::class.java)

        productsinterface = retrofit.create(Products_Interface::class.java)

        logininterface = retrofit.create(Login_Interface::class.java)
    }

    fun get_instance():Client_Data
    {
        if (null == INSTANCE)
        {
            INSTANCE = Client_Data()
        }
        return INSTANCE!!
    }

    fun getcompany(uiq:String):List<Companies>
    {
        return companiesinterface!!.get_companies(uiq)
    }

    fun getproducts(uiq:String ,companyid:Int):List<Product>
    {
        return productsinterface!!.get_products(uiq ,companyid)
    }

    fun login(email:String ,password:String):List<Login>
    {
        return logininterface!!.test_login(email ,password)
    }

}