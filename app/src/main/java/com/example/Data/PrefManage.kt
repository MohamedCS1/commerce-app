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
}