package fr.esgi.codingchainandroid.api.user.api

import com.google.gson.JsonObject
import fr.esgi.codingchainandroid.api.user.model.LoginModel
import fr.esgi.codingchainandroid.api.user.model.RegisterModel
import retrofit2.Call
import retrofit2.http.*

interface UserInterface {

    @Headers("Content-Type: application/json")
    @POST("users/authentication")
    fun loginUser(@Body loginData: LoginModel): Call<JsonObject>

    @Headers("Content-Type: application/json")
    @POST("users/registration")
    fun register(@Body registerData: RegisterModel): Call<JsonObject>

    @Headers("Content-Type: application/json")
    @GET("users/me")
    fun getUser(): Call<JsonObject>

}