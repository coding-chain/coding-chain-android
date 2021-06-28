package fr.esgi.codingchainandroid.api.user.api

import com.google.gson.JsonObject
import fr.esgi.codingchainandroid.api.user.model.LoginModel
import retrofit2.Call
import retrofit2.http.*

interface UserInterface {

    @Headers("Content-Type: application/json")
    @POST("user/login")
    fun loginUser(@Body loginData: LoginModel): Call<JsonObject>
//
//    @Headers("Content-Type: application/json")
//    @POST("user/subscribe")
//    fun subscribe(@Body data: SubscribeModel): Call<JsonObject>

//    @Cacheable
//    @Headers("Content-Type: application/json")
//    @GET("user/userFromToken")
//    fun getUser(@Header("Authorization") token: String): Call<JsonObject>

    @DELETE("user/logout/{token}")
    fun logout(@Path("token") token: String): Call<Void>



}