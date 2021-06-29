package fr.esgi.codingchainandroid.api.user.api

import com.google.gson.JsonObject
import fr.esgi.codingchainandroid.api.user.model.LoginModel
import retrofit2.Call
import retrofit2.http.*

interface UserInterface {

    @Headers("Content-Type: application/json")
    @POST("users/authentication")
    fun loginUser(@Body loginData: LoginModel): Call<JsonObject>

}