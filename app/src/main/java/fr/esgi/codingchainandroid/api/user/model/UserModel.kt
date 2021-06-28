package fr.esgi.codingchainandroid.api.user.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserModel(
    @Expose
    @SerializedName("login")
    val login: String,
    @Expose
    @SerializedName("email")
    val email: String,
    @Expose
    @SerializedName("firstname")
    val firstname: String,
    @Expose
    @SerializedName("lastname")
    val lastname: String,
    // todo real model
)
