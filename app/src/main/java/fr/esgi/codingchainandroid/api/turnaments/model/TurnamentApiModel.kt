package fr.esgi.codingchainandroid.api.turnaments.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class TurnamentApiModel(
    @Expose
    @SerializedName("id")
    val id: String,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("description")
    val description: String,
    @Expose
    @SerializedName("isPublished")
    val isPublished: Boolean,
    @Expose
    @SerializedName("startDate")
    val startDate: String,
    @Expose
    @SerializedName("endDate")
    val endDate: String,
    @Expose
    @SerializedName("stepsIds")
    val stepsIds: List<String>,
    @Expose
    @SerializedName("participationsIds")
    val participationsIds: List<String>,
)
