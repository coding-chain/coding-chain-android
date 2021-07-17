package fr.esgi.codingchainandroid.api.turnaments.api

import com.google.gson.JsonArray
import fr.esgi.codingchainandroid.api.cacheManager.api.data.Cacheable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TurnamentInterface {
    @Cacheable
    @Headers("Content-Type: application/json")
    @GET("tournaments?nameOrder=desc")
    fun getPublishedTurnaments(@Query("isPublishedFilter") isPublished: Boolean): Call<JsonArray>
}