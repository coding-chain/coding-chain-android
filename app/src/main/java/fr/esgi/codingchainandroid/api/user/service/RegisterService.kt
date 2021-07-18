package fr.esgi.codingchainandroid.api.user.service

import android.content.Context
import android.util.Log
import com.google.gson.JsonObject
import fr.esgi.codingchainandroid.api.provider.ApiClient
import fr.esgi.codingchainandroid.api.provider.AppPreferences
import fr.esgi.codingchainandroid.api.user.api.UserInterface
import fr.esgi.codingchainandroid.model.RegisterModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterService (private val context: Context) {
    private val token: String
    get() = AppPreferences.token

    fun register(registerData: RegisterModel, onResult: (JsonObject?) -> Unit) {
        val call: Call<JsonObject> = ApiClient.buildService(UserInterface::class.java, this.context)
            .register(registerData)
        call.enqueue(object : Callback<JsonObject> {

            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                Log.d("Register", "Success ${response?.body().toString()}")
                onResult(response?.body())
            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                Log.d("Register", "Failure ${call?.request()}")
                onResult(null)
            }
        })
    }

}