package fr.esgi.codingchainandroid.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.esgi.codingchainandroid.R
import fr.esgi.codingchainandroid.api.user.model.RegisterApiModel
import fr.esgi.codingchainandroid.api.user.service.RegisterService

class RegisterViewModel : ViewModel(){

    val errorLiveData = MutableLiveData<String>()

    fun register(context: Context, data: RegisterApiModel):LiveData<String>? {
        val registerService = RegisterService(context)
        registerService.register(data) { response ->
            if(response != null){
                errorLiveData.value = null
            }else{
                errorLiveData.value = context.getText(R.string.register_error).toString()
            }
        }
        return errorLiveData
    }
}