package fr.esgi.codingchainandroid.api.provider

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

object AppPreferences {
    private const val NAME = "CodingChainProject"
    private val MODE = Context.MODE_PRIVATE
    private val gsonConverter: Gson = Gson()
    private lateinit var preferences: SharedPreferences

    //SharedPreferences variables
    private val IS_LOGGED_IN = Pair("is_logged_in", false)
    private val TOKEN = Pair("token", "")
    // private val USER = Pair("user", "")

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    //an inline function to put variable and save it
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    //SharedPreferences variables getters/setters
    var isLogin: Boolean
        get() = preferences.getBoolean(IS_LOGGED_IN.first, IS_LOGGED_IN.second)
        set(value) = preferences.edit {
            it.putBoolean(IS_LOGGED_IN.first, value)
        }
    var token: String
        get() = preferences.getString(TOKEN.first, TOKEN.second) ?: ""
        set(value) = preferences.edit {
            it.putString(TOKEN.first, value)
        }
//    var user: UserModel?
//        get() {
//            val json: String? = preferences.getString(USER.first, USER.second)
//            return Gson().fromJson(json, UserModel::class.java)
//        }
//        set(user) {
//            if (user !== null) {
//                val currentUser: String = Gson().toJson(user)
//                preferences.edit {
//                    it.putString(USER.first, currentUser)
//                }
//            }
//        }
    fun clear(key: String) {
        this.preferences.edit().remove(key).apply()
    }

}