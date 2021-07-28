package fr.esgi.codingchainandroid.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.esgi.codingchainandroid.R
import fr.esgi.codingchainandroid.api.user.model.LoginApiModel
import fr.esgi.codingchainandroid.api.user.service.LoginService
import fr.esgi.codingchainandroid.model.LoginModel

class LoginViewModel : ViewModel(){

    var loginLiveData = MutableLiveData<LoginModel>()

    fun login(context: Context, data: LoginApiModel): MutableLiveData<LoginModel> {
        val loginService = LoginService(context)
        val model = LoginModel(null, "")
        loginService.loginUser(data) { response ->
            if(response != null){
                model.error = null
                model.token = response.get("token").toString()
            }else{
                model.error = context.getText(R.string.login_error).toString()
            }
            loginLiveData.value = model
        }
        return loginLiveData
    }
}