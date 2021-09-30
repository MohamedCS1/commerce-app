package com.example.phons

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Data.Client_Data
import com.example.Pojo.Login

class LoginViewModel():ViewModel() {

    val MutableLiveDataLogin = MutableLiveData<List<Login>>()

    fun test_login(email:String ,password:String)
    {
        Client_Data().get_instance().login(email ,password)
    }

}