package fr.esgi.codingchainandroid.api.user.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserModel(

    @Expose
    @SerializedName("id")
    val id: String,
    @Expose
    @SerializedName("email")
    val email: String,
    @Expose
    @SerializedName("username")
    val username: String,
    @Expose
    @SerializedName("rightIds")
    val rightIds: List<String>,
)
