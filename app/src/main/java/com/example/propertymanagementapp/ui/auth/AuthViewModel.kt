package com.example.propertymanagementapp.ui.auth

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.propertymanagementapp.data.repositories.UserRepository
import kotlinx.android.synthetic.main.activity_login.view.*

class AuthViewModel() : ViewModel(){

    var email: String? = null
    var password: String? = null

    var authListener: AuthListener? = null

    fun onLoginButtonClick(view: View){
        authListener?.onStarted()
        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            // failure
            authListener?.failure("failure")
            return
        }
        // success
        var loginResponse = UserRepository().login(email!!, password!!)
        authListener?.onSuccess(loginResponse)
    }

}