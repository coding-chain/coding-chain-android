package fr.esgi.codingchainandroid.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.esgi.codingchainandroid.api.turnaments.model.TurnamentApiModel
import fr.esgi.codingchainandroid.api.turnaments.model.TurnamentLeaderBoardApiModel
import fr.esgi.codingchainandroid.api.turnaments.service.TurnamentService
import fr.esgi.codingchainandroid.model.TournamentLeaderBoardModel
import fr.esgi.codingchainandroid.model.TournamentModel

class TournamentDetailsViewModel : ViewModel() {

    var leaderBoardLiveData = MutableLiveData<ArrayList<TournamentLeaderBoardModel>>()

    fun getLeaderBoard(context: Context, id: String): LiveData<ArrayList<TournamentLeaderBoardModel>> {
        val leaderBoardApis: ArrayList<TurnamentLeaderBoardApiModel>? = ArrayList()
        val tournamentService = TurnamentService(context);

        tournamentService.getTurnamentLeaderBoard(id){ response ->
            if(response != null){
                val items = response.get("result").asJsonArray
                items.forEach { item ->
                    val result = item.asJsonObject
                    val leaderboardItem = result.get("result")
                    val leaderboardType = object : TypeToken<TurnamentLeaderBoardApiModel>() {}.type
                    leaderBoardApis?.add(Gson().fromJson(leaderboardItem,leaderboardType))
                }
                leaderBoardLiveData.value = leaderBoardApis?.let { toTournamentLeaderBoards(it) }
            }
            else {
                leaderBoardLiveData.value = null
            }
        }
        return leaderBoardLiveData
    }


    fun toTournamentLeaderBoards(input: ArrayList<TurnamentLeaderBoardApiModel>): ArrayList<TournamentLeaderBoardModel>{
        val output = ArrayList<TournamentLeaderBoardModel>()
        input.forEach{
            output.add(toTournamentLeaderBoardModel(it))
        }
        return output
    }

    private fun toTournamentLeaderBoardModel (model : TurnamentLeaderBoardApiModel): TournamentLeaderBoardModel {
        return TournamentLeaderBoardModel(model.id, model.name, model.score, model.hasFinished)
    }

}