package fr.esgi.codingchainandroid.api.turnaments.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TurnamentLeaderBoardApiModel(
    @Expose
    @SerializedName("id")
    val id: String,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("score")
    val score: Double,
    @Expose
    @SerializedName("hasFinished")
    val hasFinished: Boolean

)
