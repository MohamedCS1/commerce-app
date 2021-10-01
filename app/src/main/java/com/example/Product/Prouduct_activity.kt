package com.example.Product

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.TestLooperManager
import android.widget.TextView
import com.example.phons.R

class Prouduct_activity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prouduct)

        val bundlue = intent.extras

        val uiq = bundlue!!.getString("uiq")

        val tv_uiq = findViewById<TextView>(R.id.tv_uiq)

        tv_uiq.text = "Your uiq : ${uiq.toString()}"
    }
}