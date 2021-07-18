package fr.esgi.codingchainandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.esgi.codingchainandroid.model.LoginModel

class LoginViewModel : ViewModel(){

    private val model = LoginModel("","","");

    val errorLiveData = MutableLiveData<String>()

    fun getUpdatedError() {
        val updatedText = model.error
        errorLiveData.postValue(updatedText)
    }

    fun setUpdatedError() {
        val updatedText = model.error
        errorLiveData.postValue(updatedText)
    }
}