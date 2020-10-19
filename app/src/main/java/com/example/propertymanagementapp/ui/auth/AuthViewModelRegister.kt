package com.example.propertymanagementapp.ui.auth

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.propertymanagementapp.data.repositories.UserRepository

class AuthViewModelRegister : ViewModel(){

    var email: String? = null
    var password: String? = null
    var name: String? = null

    var llEmail: String? = null
    var landlordEmail: String? = null
    var llPassword: String? = null
    var llName: String? = null

    var authListener: AuthListener? = null

    fun onTenantRegisterButtonClick(view: View){

        authListener?.onStarted()
        if(email.isNullOrEmpty() || password.isNullOrEmpty() || name.isNullOrEmpty()) {
            // failure
            authListener?.failure("failure")
            return
        }
        Log.d("AuthViewModelRegister", "success register")
        // success

        var tenantRegisterResponse = UserRepository().tenantRegister(email!!, password!!, name!!, "tenant")
        authListener?.onSuccess(tenantRegisterResponse)
    }

    fun onLandlordRegisterButtonClick(view: View){
        authListener?.onStarted()
        if(llEmail.isNullOrEmpty() || landlordEmail.isNullOrEmpty() || llPassword.isNullOrEmpty() || llName.isNullOrEmpty()) {
            // failure
            Log.d("fail", "fail")
            authListener?.failure("failure")
            return
        }
        Log.d("AuthViewModelRegister", "success register")
        // success

        var landlordRegisterResponse = UserRepository().landlordRegister(llEmail!!, landlordEmail!!, llPassword!!, llName!!, "landlord")
        authListener?.onSuccess(landlordRegisterResponse)
    }

}