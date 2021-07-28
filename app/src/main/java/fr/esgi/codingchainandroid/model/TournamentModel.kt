package fr.esgi.codingchainandroid.model

data class TournamentModel(
    val id: String,
    val name: String,
    val description: String,
    val isPublished: Boolean,
    val startDate: String,
    val endDate: String,
    val stepsIds: List<String>,
    val participationsIds: List<String>,
)
