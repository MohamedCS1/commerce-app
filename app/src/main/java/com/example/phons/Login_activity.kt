package com.example.phons

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import com.example.Data.PrefManage
import com.example.Pojo.Login
import com.example.Product.Prouduct_activity

class Login_activity : AppCompatActivity() {

    var et_email: EditText? = null
    var et_password: EditText? = null
    var bu_login: CardView? = null

    var loginviewmodel: LoginViewModel? = null

    var uiq:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        bu_login = findViewById(R.id.bulogin)

        loginviewmodel = LoginViewModel()

        val alertdialog = AlertDialog.Builder(this)
        val view:View = View.inflate(this ,R.layout.alert_emailorpassinvalid ,null)
        val c = alertdialog.setView(view).create()

        val alertprogress = AlertDialog.Builder(this)
        val viewprogress:View = View.inflate(this ,R.layout.progress,null)
        val p = alertprogress.setView(viewprogress).create()
        bu_login!!.setOnClickListener {


            if (et_email!!.text.isNotEmpty() && et_password!!.text.isNotEmpty())
            {
                p.show()
                p.setCancelable(false)
                p.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                loginviewmodel!!.login(et_email!!.text.toString() ,et_password!!.text.toString())
            }

        }

        val prf = PrefManage()
        loginviewmodel!!.MutableLiveDataLogin.observe(this ,object :Observer<Login>{
            override fun onChanged(t: Login?) {
               if (t!!.status == "1")
               {
                   prf.prefcreate(this@Login_activity)
                   prf.insertuiq(t.uiq)
                   val intent = Intent(this@Login_activity,Prouduct_activity::class.java)
                   intent.putExtra("uiq",t.uiq)
                   startActivity(intent)
                   Toast.makeText(this@Login_activity,"Login Successfully",Toast.LENGTH_SHORT).show()
               }
               else if (!t.checkinternt)
               {
                   Toast.makeText(this@Login_activity,"Check Your Internet",Toast.LENGTH_SHORT).show()
                   p.hide()
               }
               else
               {
                   c.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                   c.show()
                   c.setCancelable(false)
                   p.hide()
                   val buhidealert = c.findViewById<Button>(R.id.buhidealert)

                   buhidealert!!.setOnClickListener {
                       c.hide()
                   }

               }
            }

        })

    }
    override fun onStart() {
        val prf = PrefManage()
        prf.prefcreate(this)
        val uiq = prf.getuiq()
        if (uiq.isNotEmpty())
        {
            val intent = Intent(this@Login_activity,Prouduct_activity::class.java)
            intent.putExtra("uiq",uiq)
            startActivity(intent)
        }
        super.onStart()
    }
}