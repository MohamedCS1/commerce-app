package com.example.Data

import com.example.Pojo.Login
import com.example.Pojo.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface Login_Interface {

    @GET("/xml/?action=login&code=samco&user={email}&pass={pass}")
    fun test_login(@Path(value = "email") email:String, @Path(value = "pass") password:String):List<Login>

}