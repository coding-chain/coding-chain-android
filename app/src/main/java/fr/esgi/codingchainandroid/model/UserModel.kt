package fr.esgi.codingchainandroid.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserModel (
    @Expose
    @SerializedName("id")
    val id: String,
    @Expose
    @SerializedName("email")
    var email: String,
    @Expose
    @SerializedName("username")
    var username: String,
    @Expose
    @SerializedName("rightIds")
    var rightIds: List<String>,

    var rights: List<RightModel>,
    val error: String
)