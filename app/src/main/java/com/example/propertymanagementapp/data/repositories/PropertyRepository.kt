package com.example.propertymanagementapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.propertymanagementapp.data.model.PostImageResponse
import com.example.propertymanagementapp.data.model.Property
import com.example.propertymanagementapp.data.model.PropertyResponse
import com.example.propertymanagementapp.data.network.MyApi
import com.example.propertymanagementapp.helpers.TokenManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

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

    fun newPropertyWithImage(address: String, city: String, country: String, purchasePrice: String, state : String, url: String) {
        Log.d("PropertyRepo newProp", "adding new property with image")
        val propertyResponse = MutableLiveData<String>()
        Log.d("PropertyRepo URL", url)
        MyApi().addProperty(address, city, country, purchasePrice, state, url)
            .enqueue(object: retrofit2.Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.isSuccessful){
                        Log.d("suc_response" , response.toString())
                        propertyResponse.value = "Add Property success"
                    } else {
                        propertyResponse.value = "Add Property Error"
                        Log.d("Prop Repository resp", response.toString())
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
        var properties = MutableLiveData<ArrayList<Property>>()
        MyApi().getProperties()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<PropertyResponse>(){
                override fun onSuccess(response: PropertyResponse) {
                    //add response.data to list
                    properties.value = response.data
                    Log.d("Suc Properties Response", "Nice job!")
                }

                override fun onError(e: Throwable) {
                    Log.d("bad properties Response", e.toString())
                }

            })
        return properties
    }

    fun getPropertiesById(id: String): LiveData<ArrayList<Property>> {
        var properties = MutableLiveData<ArrayList<Property>>()
        MyApi().getPropertiesByUserId()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<PropertyResponse>(){
                override fun onSuccess(response: PropertyResponse) {
                    //add response.data to list
                    properties.value = response.data
                    Log.d("Suc Properties Response", "Nice job!")
                }

                override fun onError(e: Throwable) {
                    Log.d("bad properties Response", e.toString())
                }

            })
        return properties
    }


    fun uploadImage(path: String) {
        var file = File(path)

        var requestFile = RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
        var body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        Log.d("Picture Upload:", "Attempting...")
        MyApi().uploadImage(body)
            .enqueue(object: Callback<PostImageResponse> {
                override fun onResponse(
                    call: Call<PostImageResponse>,
                    response: Response<PostImageResponse>
                ) {
                    Log.d("Picture Upload Success:", response.body()!!.toString())
                    TokenManager().storeImageLocation(response.body()!!.data.location)
                }

                override fun onFailure(call: Call<PostImageResponse>, t: Throwable) {
                    Log.d("Picture Upload Fail:", t.toString())
                }

            })
    }

}

