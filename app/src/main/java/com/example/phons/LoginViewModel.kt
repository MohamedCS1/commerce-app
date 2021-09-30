package com.example.phons

import android.os.Build
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Pojo.Login
import java.net.HttpURLConnection
import java.net.URL

class LoginViewModel():ViewModel() {

    val MutableLiveDataLogin = MutableLiveData<Login>()

    fun login(email:String ,password:String)
    {
        val t = Thread(Runnable {
            fun sendGet() {
                val url = URL("https://app.mytasks.click/xml/?action=login&code=samco&user=$email&pass=$password")

                with(url.openConnection() as HttpURLConnection) {
                    requestMethod = "GET"

                    inputStream.bufferedReader().use {
                        var status:String? = null
                        var msg:String? = null
                        var uiq:String? = null
                        val arr = arrayListOf<String>()
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            it.lines().forEach { line ->

                                val b = line.substringAfter("<data>").substringBefore("</data>").substringAfter(">").substringBefore("<")
                                Log.d("line",b)
                                if (b != "")
                                {
                                    arr.add(b)
                                }
//                                status = line[0].toString()
//                                msg = line[1].toString()
//                                uiq = line[2].toString()
//                                val x =  Login(status!!.toInt() ,msg.toString() ,uiq.toString())
//                                MutableLiveDataLogin.value = x
                            }
                            Log.d("line" ,arr.size.toString())
                            if(arr.size == 3)
                            {
                                status = arr[0]
                                msg = arr[1]
                                uiq = ""
                                arr.clear()
                            }
                            else
                            {
                                status = arr[0]
                                msg = arr[1]
                                uiq = arr[3]
                                arr.clear()
                            }
                            MutableLiveDataLogin.postValue(Login(status.toString() ,msg.toString() ,uiq.toString()))

                        }
                    }
                }
            }
            sendGet()
        })

        t.start()
    }

}