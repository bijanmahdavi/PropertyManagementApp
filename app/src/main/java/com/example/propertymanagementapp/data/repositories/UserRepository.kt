package com.example.propertymanagementapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.propertymanagementapp.data.model.LoginResponse
import com.example.propertymanagementapp.data.model.PropertyResponse
import com.example.propertymanagementapp.data.network.MyApi
import com.example.propertymanagementapp.helpers.TokenManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    fun login(email:String, password: String): LiveData<String> {
        Log.d("UserRepository login", "Logging in ?")
        val loginResponse = MutableLiveData<String>()
        Log.d("UserRepository loginres", loginResponse.toString())
        MyApi().login(email, password)
            .enqueue(object: Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if(response.body() != null){
                        Log.d("suc_response" , response.toString())
                        loginResponse.value = "Login success"
                        var token = response.body()!!.token
                        Log.d("login_response", response.body().toString())
                        TokenManager().storeToken(token)
                        Log.d("Token", TokenManager().isLoggedIn().toString())
                        //start next activity
                    } else {
                        loginResponse.value = "Account not registered"
                        Log.d("User Repository", "This account does not exist")
                        Log.d("User Repository resp", response.toString())
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("fail_response" , t.message!!)
                    loginResponse.value = t.message
                }

            })

        return loginResponse
    }

    fun tenantRegister(email:String, password: String, name: String, type: String): LiveData<String> {
        Log.d("UserRepository register", "registering ?")
        val registerResponse = MutableLiveData<String>()
        Log.d("UserRepository loginres", registerResponse.toString())
        MyApi().tenantRegister(email, password, name, type)
            .enqueue(object: Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.isSuccessful){
                        Log.d("suc_response" , response.toString())
                        registerResponse.value = "Register success"
                        //start next activity
                    } else {
                        registerResponse.value = "Account already registered"
                        Log.d("User Repository", "This account already exists")
                        Log.d("User Repository resp", response.toString())
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("fail_response" , t.message!!)
                    registerResponse.value = t.message
                }

            })

        return registerResponse
    }

    fun landlordRegister(email:String, landlordEmail:String, password: String, name: String, type: String): LiveData<String> {
        Log.d("UserRepository register", "registering ?")
        val registerResponse = MutableLiveData<String>()
        Log.d("UserRepository loginres", registerResponse.toString())
        MyApi().landlordRegister(email, landlordEmail, name, password, type)
            .enqueue(object: Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.isSuccessful){
                        Log.d("suc_response" , response.toString())
                        registerResponse.value = "Register success"
                        //start next activity
                    } else {
                        registerResponse.value = "Account already registered"
                        Log.d("User Repository", "This account already exists")
                        Log.d("User Repository resp", response.toString())
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("fail_response" , t.message!!)
                    registerResponse.value = t.message
                }

            })

        return registerResponse
    }

    fun getProperties(){
        MyApi().getProperties()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<PropertyResponse>(){
                override fun onSuccess(response: PropertyResponse) {
                    //add response.data to list
                    Log.d("Suc Properties Response", "Nice job!")
                }

                override fun onError(e: Throwable) {
                    Log.d("bad properties Response", e.toString())
                }

            })
    }
}