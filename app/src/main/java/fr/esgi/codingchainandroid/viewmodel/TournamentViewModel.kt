package fr.esgi.codingchainandroid.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.esgi.codingchainandroid.api.turnaments.model.TurnamentApiModel
import fr.esgi.codingchainandroid.api.turnaments.service.TurnamentService
import fr.esgi.codingchainandroid.model.TournamentModel

class TournamentViewModel : ViewModel() {

    var tournamentsLiveData = MutableLiveData<ArrayList<TournamentModel>>()

    fun getTournaments(context: Context): LiveData<ArrayList<TournamentModel>>? {
        var tournaments: ArrayList<TurnamentApiModel>? = ArrayList()
        val tournamentService = TurnamentService(context);
        tournamentService.getPublishedTurnaments { response ->
            if (response != null) {
                val items = response.get("result").asJsonArray
                items.forEach { item ->
                    val result = item.asJsonObject
                    val turnament = result.get("result")
                    val turnamentType = object : TypeToken<TurnamentApiModel>() {}.type
                    tournaments?.add(Gson().fromJson(turnament, turnamentType))
                }
                tournamentsLiveData.value = tournaments?.let { toTournaments(it) }
            }
            else{
                tournaments = null;
            }
        }
        return tournamentsLiveData
    }

    fun toTournaments(input: ArrayList<TurnamentApiModel>): ArrayList<TournamentModel>{
        val output = ArrayList<TournamentModel>()
        input.forEach{
            output.add(toTournamentModel(it))
        }
        return output
    }

    fun toTournamentsApi(input: ArrayList<TournamentModel>): ArrayList<TurnamentApiModel>{
        val output = ArrayList<TurnamentApiModel>()
        input.forEach{
            output.add(toTournamentApiModel(it))
        }
        return output
    }

    private fun toTournamentModel (model : TurnamentApiModel): TournamentModel {
        return TournamentModel(model.id, model.name, model.description,
            model.isPublished, model.startDate, model.endDate, model.stepsIds, model.participationsIds)
    }

    private fun toTournamentApiModel (model : TournamentModel): TurnamentApiModel {
        return TurnamentApiModel(model.id, model.name, model.description,
            model.isPublished, model.startDate, model.endDate, model.stepsIds, model.participationsIds)
    }
}