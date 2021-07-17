package fr.esgi.codingchainandroid.api.turnaments.service

import android.content.Context
import android.util.Log
import com.google.gson.JsonObject
import fr.esgi.codingchainandroid.api.provider.ApiClient
import fr.esgi.codingchainandroid.api.team.api.TeamInterface
import fr.esgi.codingchainandroid.api.turnaments.api.TurnamentInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TurnamentService(private val context: Context) {
    fun getPublishedTurnaments(onResult: (JsonObject?) -> Unit) {
        val call: Call<JsonObject> =
            ApiClient.buildService(TurnamentInterface::class.java, this.context)
                .getPublishedTurnaments(true);
        call.enqueue(object : Callback<JsonObject> {

            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                Log.d("Turnament", "Success ${response?.body().toString()}")
                onResult(response?.body())
            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                Log.d("Turnament", "Failure ${call?.request()}")
                onResult(null)
            }
        })
    }

    fun getTurnamentLeaderBoard(turnamentId: String, onResult: (JsonObject?) -> Unit){
        val call: Call<JsonObject> =
            ApiClient.buildService(TurnamentInterface::class.java, this.context)
                .getTurnamentsLeaderboard(turnamentId);
        call.enqueue(object : Callback<JsonObject> {

            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                Log.d("Turnament", "Success ${response?.body().toString()}")
                onResult(response?.body())
            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                Log.d("Turnament", "Failure ${call?.request()}")
                onResult(null)
            }
        })
    }

    fun getTurnamentParticipations(turnamentId: String, onResult: (JsonObject?)-> Unit){
        val call: Call<JsonObject> =
            ApiClient.buildService(TurnamentInterface::class.java, this.context)
                .getTurnamentsParticipation(turnamentId);
        call.enqueue(object : Callback<JsonObject> {

            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                Log.d("Turnament", "Success ${response?.body().toString()}")
                onResult(response?.body())
            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                Log.d("Turnament", "Failure ${call?.request()}")
                onResult(null)
            }
        });
    }
}