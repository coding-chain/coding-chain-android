package fr.esgi.codingchainandroid.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterModel(
    @Expose
    @SerializedName("email")
    var email: String?,
    @Expose
    @SerializedName("password")
    var password: String?,
    var confirmPassword: String?,
    var error: String?,
    @Expose
    @SerializedName("username")
    var username: String?,
)
