package fr.esgi.codingchainandroid.api.user.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginModel(
    @Expose
    @SerializedName("login")
    val login: String,
    @Expose
    @SerializedName("password")
    val password: String,
)
