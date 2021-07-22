package fr.esgi.codingchainandroid.viewmodel

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.esgi.codingchainandroid.api.turnaments.model.TurnamentModel
import fr.esgi.codingchainandroid.api.turnaments.service.TurnamentService

class TournamentViewModel : ViewModel() {

    var tournamentsLiveData = MutableLiveData<ArrayList<TurnamentModel>>()

    fun getTournaments(context: Context): LiveData<ArrayList<TurnamentModel>>? {
        var tournaments: ArrayList<TurnamentModel>? = ArrayList()
        val tournamentService = TurnamentService(context);
        tournamentService.getPublishedTurnaments { response ->
            if (response != null) {
                val items = response.get("result").asJsonArray
                items.forEach { item ->
                    val result = item.asJsonObject
                    val turnament = result.get("result")
                    val turnamentType = object : TypeToken<TurnamentModel>() {}.type
                    tournaments?.add(Gson().fromJson(turnament, turnamentType))
                }
                tournamentsLiveData.value = tournaments
            }
            else{
                tournaments = null;
            }
        }
        return tournamentsLiveData
    }
}