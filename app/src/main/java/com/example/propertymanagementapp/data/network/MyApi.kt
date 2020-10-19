package com.example.propertymanagementapp.data.network

import com.example.propertymanagementapp.app.Config
import com.example.propertymanagementapp.data.model.LoginResponse
import com.example.propertymanagementapp.data.model.PostImageResponse
import com.example.propertymanagementapp.data.model.PropertyResponse
import com.example.propertymanagementapp.helpers.TokenManager
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {


    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("auth/register")
    fun tenantRegister(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("type") type: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("auth/register")
    fun landlordRegister(
        @Field("email") email: String,
        @Field("landlordEmail") landlordEmail: String,
        @Field("name") name: String,
        @Field("password") password: String,
        @Field("type") type: String
    ): Call<ResponseBody>

    @GET("property")
    fun getProperties(): Single<PropertyResponse>

    @GET("property/user/5f83be04c2ec1600179f8cf5"/*${TokenManager().getUserId()}"*/)
    fun getPropertiesByUserId(): Single<PropertyResponse>

    @FormUrlEncoded
    @POST("property")
    fun addProperty(
        @Field("address") address: String,
        @Field("city") city: String,
        @Field("country") country: String,
        @Field("purchasePrice") purchasePrice: String,
        @Field("state") state: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("property")
    fun addProperty(
        @Field("address") address: String,
        @Field("city") city: String,
        @Field("country") country: String,
        @Field("purchasePrice") purchasePrice: String,
        @Field("state") state: String,
        @Field("location") location: String
    ): Call<ResponseBody>

    @Multipart
    @POST("upload/property/picture")
    fun uploadImage(
        @Part image: MultipartBody.Part
    ): Call<PostImageResponse>



//    @FormUrlEncoded
//    @POST("auth/login")
//    fun login2(
//       @Body user: User
//    ): Call<ResponseBody>


    companion object{
        var something = TokenManager().getUserId()
        operator fun invoke(): MyApi {
            return Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                 //rxjava adapter
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(ApiWorker.client)
                .build()
                .create(MyApi::class.java)
        }
    }
}