package fr.esgi.codingchainandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.esgi.codingchainandroid.model.RegisterModel

class RegisterViewModel : ViewModel(){

    private val model = RegisterModel("","", "","", "");

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