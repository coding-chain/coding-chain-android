package fr.esgi.codingchainandroid.api.user.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterModel(
    @Expose
    @SerializedName("email")
    var email: String?,
    @Expose
    @SerializedName("password")
    var password: String?,
    @Expose
    @SerializedName("username")
    var username: String?,
)
