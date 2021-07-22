package fr.esgi.codingchainandroid.viewmodel

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import fr.esgi.codingchainandroid.R
import fr.esgi.codingchainandroid.api.right.service.RightService
import fr.esgi.codingchainandroid.api.user.service.UserService
import fr.esgi.codingchainandroid.model.RegisterModel
import fr.esgi.codingchainandroid.model.RightModel
import fr.esgi.codingchainandroid.model.TeamModel
import fr.esgi.codingchainandroid.model.UserModel
import kotlinx.android.synthetic.main.account_activity.*

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
                val user = UserModel(
                    response.get("id").asString,
                    response.get("email").asString,
                    response.get("username").asString,
                    rightIds,
                    rightsList,
                    ""
                                )
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
}