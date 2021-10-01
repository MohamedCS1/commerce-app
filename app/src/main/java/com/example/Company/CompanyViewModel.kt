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

    val MutableLiveDataCompaby = MutableLiveData<ArrayList<Companies>>()

    val array_company = arrayListOf<Companies>()

    fun getcompany(uiq:String)
    {
        val t = Thread(Runnable {
            fun sendGet() {
                val url = URL("https://app.mytasks.click/xml/?action=getcompanies&uiq=$uiq")
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

                            var compid:String? = null
                            var company_title:String? = null
                            var lastversion:String? = null
                            var image_link:String? = null

                            val finalarray = arrayListOf<String>()

                            var f = 0
                            for (i in arraysplit)
                            {
                                if (i == "company" && f != 0)
                                {
                                    finalarray.add(" ")
                                    continue
                                }

                                if (i == "" || i == "  " || i == "   " || i == "data" || i == "/data" || i == "company" || i == "compid" || i == "company_title" || i == "lastversion" || i == "/lastversion"|| i == "/company" || i == "/compid" || i == "/company_title" || i == "image_link" || i == "/image_link")
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
                                if (image_link != " ")
                                {
                                    array_company.add(Companies(compid.toString() ,company_title.toString() ,lastversion.toString() ,image_link.toString()))
                                    image_link = ""

                                }
                                else
                                {
                                    array_company.add(Companies(compid.toString() ,company_title.toString() ,lastversion.toString(),""))
                                }
                            }


                            var c = 0
                            for (i in finalarray)
                            {

                                if (c == 0)
                                {
                                    compid = i
                                    c++
                                    continue
                                }
                                else if (c == 1)
                                {
                                    company_title = i
                                    c++
                                    continue
                                }
                                else if (c == 2)
                                {
                                    lastversion = i
                                    c++
                                    continue
                                }
                                else if (c == 3 && i[0].toString() == "/")
                                {
                                    image_link = i
                                    continue
                                }
                                else if (i == " ")
                                {
                                    if (image_link != " ")
                                    {
                                        insert()
                                        c = 0
                                        image_link = ""
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
                            MutableLiveDataCompaby.postValue(array_company)
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