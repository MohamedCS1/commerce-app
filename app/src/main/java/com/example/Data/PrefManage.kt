package com.example.Data

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class PrefManage {
    var pref: SharedPreferences? = null

    var editor: SharedPreferences.Editor? = null

    var PRIVATE_MODE = 0

    private val PREF_NAME = "MY_PREF"

    private val key_uiq = "uiq"


    private val key_compid = "compid"

    private val key_email = "email"

    private val key_password = "password"

    private val key_code = "code"

    private val key_is_one_page = "on_page"

    var context:Context? = null

    @SuppressLint("CommitPrefEdits")
    fun prefcreate(context: Context?)
    {
        this.context = context
        pref = context!!.getSharedPreferences(PREF_NAME ,PRIVATE_MODE)
        editor = pref!!.edit()
    }

    fun insertuiq(uiq:String)
    {
        editor?.putString(key_uiq ,uiq)
        editor?.apply()
    }

    fun getuiq():String
    {
        return pref!!.getString(key_uiq,"").toString()
    }

    fun insertcompid(compid:String)
    {
        editor?.putString(key_compid ,compid)
        editor?.apply()
    }

    fun getcompid():String
    {
        return pref!!.getString(key_compid ,"").toString()
    }

    fun insertemail(email:String)
    {
        editor?.putString(key_email ,email)
        editor?.apply()
    }

    fun getemail():String
    {
        return pref!!.getString(key_email ,"").toString()
    }

    fun insertpassword(password:String)
    {
        editor?.putString(key_password ,password)
        editor?.apply()
    }

    fun getpassword():String
    {
        return pref!!.getString(key_password ,"").toString()
    }

    fun insertcode(code: String)
    {
        editor?.putString(key_code ,code)
        editor?.apply()
    }

    fun getcode():String
    {
        return pref!!.getString(key_code ,"").toString()
    }

    fun insert_one_page(boolean: Boolean)
    {
        editor?.putBoolean(key_is_one_page ,boolean)
        editor?.apply()
    }

    fun get_is_one():Boolean
    {
        return pref!!.getBoolean(key_is_one_page,false)
    }

}