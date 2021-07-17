package fr.esgi.codingchainandroid.api.turnaments.service

import android.content.Context
import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import fr.esgi.codingchainandroid.api.provider.ApiClient
import fr.esgi.codingchainandroid.api.team.api.TeamInterface
import fr.esgi.codingchainandroid.api.turnaments.api.TurnamentInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TurnamentService(private val context: Context) {
    fun getPublishedTurnaments(onResult: (JsonArray?) -> Unit) {
        val call: Call<JsonArray> =
            ApiClient.buildService(TurnamentInterface::class.java, this.context)
                .getPublishedTurnaments(true);
        call.enqueue(object : Callback<JsonArray> {

            override fun onResponse(call: Call<JsonArray>?, response: Response<JsonArray>?) {
                Log.d("Team", "Success ${response?.body().toString()}")
                onResult(response?.body())
            }

            override fun onFailure(call: Call<JsonArray>?, t: Throwable?) {
                Log.d("Team", "Failure ${call?.request()}")
                onResult(null)
            }
        })
    }
}