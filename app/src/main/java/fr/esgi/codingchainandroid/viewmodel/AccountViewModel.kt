package fr.esgi.codingchainandroid.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import fr.esgi.codingchainandroid.api.right.service.RightService
import fr.esgi.codingchainandroid.api.turnaments.model.TurnamentApiModel
import fr.esgi.codingchainandroid.api.user.model.UserApiModel
import fr.esgi.codingchainandroid.api.user.service.UserService
import fr.esgi.codingchainandroid.model.RegisterModel
import fr.esgi.codingchainandroid.model.RightModel
import fr.esgi.codingchainandroid.model.UserModel

class AccountViewModel : ViewModel(){

    val userLiveData = MutableLiveData<UserModel>()
    val userUpdateLiveData = MutableLiveData<String>()

    fun updateMe(context: Context, data:RegisterModel):LiveData<String>? {
        val userService = UserService(context)
        userService.updateMe(data) { response ->
            if(response != null){
                userUpdateLiveData.value = "SUCCESS"
            }else{
                userUpdateLiveData.value = "ERROR"
            }
        }
        return userUpdateLiveData
    }

    fun getUser(context: Context): LiveData<UserModel>? {
        val userService = UserService(context)
        val rightsList: MutableList<RightModel> = ArrayList()
        val rightIds: MutableList<String> = ArrayList()

        userService.getUser() { response ->
            if (response !== null) {

                val userType = object : TypeToken<UserApiModel>() {}.type
                val user = UserModel(
                    response.get("email").asString,
                    response.get("username").asString,
                    ArrayList(), null)
                userLiveData.value = user
                val jsonRightIds = response.get("rightIds").asJsonArray
                for (i in 0 until jsonRightIds.size()) {
                    rightIds.add(jsonRightIds.get(i).asString)
                }
                if(rightIds.isNotEmpty()){
                    val rightService = RightService(context)
                    var rightResponse: JsonObject?
                    rightIds.forEach {
                        rightService.getOneById(it) { rResponse ->
                            rightResponse = rResponse!!.get("result").asJsonObject
                            if (rightResponse != null) {
                                rightsList.add(RightModel(rightResponse!!.get("name").asString))
                                user.rights = rightsList
                                userLiveData.value = user
                            }
                        }
                    }
                }
            }
        }
        return userLiveData
    }

    private fun toUserModel (model : UserApiModel): UserModel {
        return UserModel(model.email, model.username, null, null)
    }

}