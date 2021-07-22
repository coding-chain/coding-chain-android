package fr.esgi.codingchainandroid.model

data class UserModel (
    var email: String?,
    var username: String?,
    var rights: List<RightModel>?,
    val error: String?
)