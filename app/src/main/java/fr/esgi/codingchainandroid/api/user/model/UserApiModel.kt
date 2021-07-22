package fr.esgi.codingchainandroid.api.user.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserApiModel (
    @Expose
    @SerializedName("id")
    val id: String?,
    @Expose
    @SerializedName("email")
    var email: String?,
    @Expose
    @SerializedName("username")
    var username: String?,
    @Expose
    @SerializedName("rightIds")
    var rightIds: List<String>?,
)