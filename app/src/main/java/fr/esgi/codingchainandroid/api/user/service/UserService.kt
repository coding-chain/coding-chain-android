package fr.esgi.codingchainandroid.api.user.service

import android.content.Context
import android.util.Log
import com.google.gson.JsonObject
import fr.esgi.codingchainandroid.api.provider.ApiClient
import fr.esgi.codingchainandroid.api.provider.AppPreferences
import fr.esgi.codingchainandroid.api.user.api.UserInterface
import fr.esgi.codingchainandroid.api.user.model.LoginModel
import fr.esgi.codingchainandroid.api.user.model.RegisterModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserService (private val context: Context) {

    fun getUser(onResult: (JsonObject?) -> Unit) {
        val call: Call<JsonObject> = ApiClient.buildService(UserInterface::class.java, this.context)
            .getUser()
        call.enqueue(object : Callback<JsonObject> {

            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                Log.d("User", "Success ${response?.body().toString()}")
                onResult(response?.body())
            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                Log.d("User", "Failure ${call?.request()}")
                onResult(null)
            }
        })
    }

    fun updateMe(data: RegisterModel, onResult: (JsonObject?) -> Unit) {
        val call: Call<JsonObject> = ApiClient.buildService(UserInterface::class.java, this.context)
            .updateMe(data)
        call.enqueue(object : Callback<JsonObject> {

            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                if(response?.errorBody() != null){
                    onResult(null)
                }else{
                    Log.d("User update", "Success ${response?.body().toString()}")
                    if(response?.body() != null){
                        onResult(response.body())
                    }else {
                        onResult(JsonObject())
                    }
                }
            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                Log.d("User update", "Failure ${call?.request()}")
                onResult(null)
            }
        })
    }

}