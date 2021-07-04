package fr.esgi.codingchainandroid.api.team.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TeamModel(
    @Expose
    @SerializedName("id")
    val id: String,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("membersIds")
    val membersIds: List<String>,
/*    @Expose
    @SerializedName("participationIds")
    val participationIds: List<String>,*/
)
