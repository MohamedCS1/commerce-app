package com.example.phons

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import com.example.Pojo.Login
import java.net.HttpURLConnection
import java.net.URL

class Login_activity : AppCompatActivity() {

    var et_email: EditText? = null
    var et_password: EditText? = null
    var bu_login: CardView? = null

    var loginviewmodel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        bu_login = findViewById(R.id.bulogin)

        loginviewmodel = LoginViewModel()

        bu_login!!.setOnClickListener {

            if (et_email!!.text.isNotEmpty() && et_password!!.text.isNotEmpty())
            {
                loginviewmodel!!.login(et_email!!.text.toString() ,et_password!!.text.toString())
            }

        }

        loginviewmodel!!.MutableLiveDataLogin.observe(this ,object :Observer<Login>{
            override fun onChanged(t: Login?) {
               if (t!!.status == "1")
               {
                   Toast.makeText(this@Login_activity,"Login Successfully ${t.uiq}",Toast.LENGTH_SHORT).show()
               }
               else
               {
                   Toast.makeText(this@Login_activity,"Oops",Toast.LENGTH_SHORT).show()
               }
            }

        })

    }
}