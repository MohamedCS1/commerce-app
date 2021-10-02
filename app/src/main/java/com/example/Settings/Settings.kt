package com.example.Settings

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.Company.CompanyViewModel
import com.example.Data.CompanyDatabase
import com.example.Data.PrefManage
import com.example.Pojo.Companies
import com.example.phons.R

class Settings : AppCompatActivity() {

    var bu_import_data:Button? = null
    var companydatabase:CompanyDatabase? = null
    var CompanyViewModel: CompanyViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        val prf = PrefManage()
        prf.prefcreate(this)

        val uiq = prf.getuiq()

        bu_import_data = findViewById(R.id.bu_import_data)

        companydatabase = CompanyDatabase.getInstance(this)

        CompanyViewModel = CompanyViewModel(this)

        CompanyViewModel!!.getcompany(uiq)

        CompanyViewModel!!.MutableLiveDataCompaby.observe(this ,object : Observer<ArrayList<Companies>> {

            override fun onChanged(t: ArrayList<Companies>?) {
                Thread( Runnable() {
                    Log.d("chang" ,t.toString())

                    if(t!![0].compid.toString() == "null")
                    {
                        Toast.makeText(this@Settings ,"Check Your Internet" ,Toast.LENGTH_SHORT).show()
                        companydatabase?.CompanyDao()?.claer_db()

                    }

                    for (i in t)
                    {
                        companydatabase?.CompanyDao()?.insertCompany(i)
                    }
                }).start()
            }

        })

    }
}