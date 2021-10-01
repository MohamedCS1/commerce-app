package com.example.Company

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.phons.R

class Company_activity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")

    var CompanyViewModel:CompanyViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)

        val bundlue = intent.extras

        val uiq = bundlue!!.getString("uiq")

        val tv_uiq = findViewById<TextView>(R.id.tv_uiq)

        tv_uiq.text = "Your uiq : ${uiq.toString()}"

        CompanyViewModel = CompanyViewModel()

        CompanyViewModel!!.getcompany(uiq!!)
    }
}