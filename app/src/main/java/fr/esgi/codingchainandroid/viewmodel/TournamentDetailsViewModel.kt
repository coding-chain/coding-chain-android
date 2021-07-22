package fr.esgi.codingchainandroid.viewmodel

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.esgi.codingchainandroid.api.turnaments.model.TurnamentLeaderBoardModel
import fr.esgi.codingchainandroid.api.turnaments.model.TurnamentModel
import fr.esgi.codingchainandroid.api.turnaments.service.TurnamentService
import kotlinx.android.synthetic.main.turnament_details.*

class TournamentDetailsViewModel : ViewModel() {

    var leaderBoardLiveData = MutableLiveData<ArrayList<TurnamentLeaderBoardModel>>()

    fun getLeaderBoard(context: Context, id: String): LiveData<ArrayList<TurnamentLeaderBoardModel>> {
        val leaderBoards: ArrayList<TurnamentLeaderBoardModel>? = ArrayList()
        val tournamentService = TurnamentService(context);

        tournamentService.getTurnamentLeaderBoard(id){ response ->
            if(response != null){
                val items = response.get("result").asJsonArray
                items.forEach { item ->
                    val result = item.asJsonObject
                    val leaderboardItem = result.get("result")
                    val leaderboardType = object : TypeToken<TurnamentLeaderBoardModel>() {}.type
                    leaderBoards?.add(Gson().fromJson(leaderboardItem,leaderboardType))
                }
                leaderBoardLiveData.value = leaderBoards
            }
            else {
                leaderBoardLiveData.value = null
            }
        }
        return leaderBoardLiveData
    }
}