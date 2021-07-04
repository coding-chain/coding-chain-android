package fr.esgi.codingchainandroid.api.team.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface TeamInterface {

    @Headers("Content-Type: application/json")
    @GET("teams/{id}")
    fun getOneById(@Path("id") id: String): Call<JsonObject>

}