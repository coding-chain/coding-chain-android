package fr.esgi.codingchainandroid.api.user.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterModel(
    @Expose
    @SerializedName("email")
    val email: String,
    @Expose
    @SerializedName("password")
    val password: String,
    @Expose
    @SerializedName("username")
    val username: String,
)
