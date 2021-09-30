package com.example.phons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.Data.Client_Data

class Login_activity : AppCompatActivity() {

    var et_email:EditText? = null
    var et_password:EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)

        val retrofit = Client_Data()

        retrofit.logininterface

    }
}