package fr.esgi.codingchainandroid.api.right.service

import android.content.Context
import android.util.Log
import com.google.gson.JsonObject
import fr.esgi.codingchainandroid.api.provider.ApiClient
import fr.esgi.codingchainandroid.api.right.api.RightInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RightService (private val context: Context) {

    fun getOneById(id: String, onResult: (JsonObject?) -> Unit) {
        val call: Call<JsonObject> = ApiClient.buildService(RightInterface::class.java, this.context)
            .getOneById(id)
        call.enqueue(object : Callback<JsonObject> {

            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                Log.d("Right", "Success ${response?.body().toString()}")
                onResult(response?.body())
            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                Log.d("Right", "Failure ${call?.request()}")
                onResult(null)
            }
        })
    }

}