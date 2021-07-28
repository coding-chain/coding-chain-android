package fr.esgi.codingchainandroid.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterModel(
    var email: String?,
    var password: String?,
    var confirmPassword: String?,
    var error: String?,
    var username: String?,
)
