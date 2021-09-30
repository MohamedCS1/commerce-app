package com.example.Data

import com.example.Pojo.Companies
import retrofit2.http.GET
import retrofit2.http.Path

interface Compani_Interface {
    @GET("/xml/?action=getcompanies&uiq={uiq}")
    fun get_companies(@Path(value = "uiq") uiq:String):List<Companies>
}