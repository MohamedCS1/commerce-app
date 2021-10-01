package com.example.Company

import android.os.Build
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Pojo.Companies
import com.example.Pojo.Login
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class CompanyViewModel:ViewModel() {

    val MutableLiveDataCompaby = MutableLiveData<Companies>()

    fun getcompany(uiq:String)
    {
        val t = Thread(Runnable {
            fun sendGet() {
                val url = URL("https://app.mytasks.click/xml/?action=getcompanies&uiq=$uiq")
                try {
                    with(url.openConnection() as HttpURLConnection) {
                        requestMethod = "GET"

                        inputStream.bufferedReader().use {

                            val arr = arrayListOf<String>()
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                var index = 0

                                it.lines().forEach { line ->
                                    index++
                                    if (index == 8)
                                    {
                                        arr.add(line)
                                    }
                                }

                            }

                            for (i in arr)
                            {
                                Log.d("line" ,i.toString())
                            }
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