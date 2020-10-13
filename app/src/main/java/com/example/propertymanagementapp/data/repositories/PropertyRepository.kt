package com.example.propertymanagementapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.propertymanagementapp.data.model.Property
import com.example.propertymanagementapp.data.model.PropertyResponse
import com.example.propertymanagementapp.data.network.MyApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class PropertyRepository {

    fun newProperty(address: String, city: String, country: String, purchasePrice: String, state : String) {
        Log.d("PropertyRepo newProp", "adding new property")
        val propertyResponse = MutableLiveData<String>()
        MyApi().addProperty(address, city, country, purchasePrice, state)
            .enqueue(object: retrofit2.Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.isSuccessful){
                        Log.d("suc_response" , response.toString())
                        propertyResponse.value = "Add Property success"
                    } else {
                        propertyResponse.value = "Account already registered"
                        Log.d("User Repository", "This account already exists")
                        Log.d("User Repository resp", response.toString())
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("fail_response" , t.message!!)
                    propertyResponse.value = t.message
                }

            })

        //return propertyResponse
    }

    fun getProperties(): LiveData<ArrayList<Property>> {
        var data = ArrayList<Property>()
        var something = MutableLiveData<ArrayList<Property>>()
        MyApi().getProperties()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<PropertyResponse>(){
                override fun onSuccess(response: PropertyResponse) {
                    //add response.data to list
                    something.value = response.data
                    Log.d("Suc Properties Response", "Nice job!")
                }

                override fun onError(e: Throwable) {
                    Log.d("bad properties Response", e.toString())
                }

            })
        return something
    }

}