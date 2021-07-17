package fr.esgi.codingchainandroid.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.esgi.codingchainandroid.model.TeamModel
import fr.esgi.codingchainandroid.api.team.service.TeamService
import fr.esgi.codingchainandroid.api.user.service.UserService

class TeamViewModel : ViewModel() {

    var teamLiveData = MutableLiveData<ArrayList<TeamModel>>()

    fun getMyTeam(context: Context): LiveData<ArrayList<TeamModel>>? {
        val teams: ArrayList<TeamModel> = ArrayList()
        val teamService = TeamService(context);
        val meService = UserService(context)

        meService.getUser { meResponse ->
            if (meResponse !== null) {
                val jsonTeamIds = meResponse.get("teamIds").asJsonArray
                val teamIds: MutableList<String> = ArrayList()
                jsonTeamIds.forEach { jsonTeamId -> teamIds.add(jsonTeamId.asString) }
                if(teamIds.size >0){
                    teamIds.forEach{
                        teamService.getOneById(it) { teamResponse ->
                            if (teamResponse !== null) {
                                val jsonTeam = teamResponse.get("result").asJsonObject
                                val jsonMemberIds = jsonTeam.get("membersIds").asJsonArray
                                val memberIds: MutableList<String> = ArrayList()
                                jsonMemberIds.forEach{ jsonMemberId ->
                                    memberIds.add(jsonMemberId.asString)
                                }
                                val team = TeamModel(
                                    jsonTeam.get("id").asString,
                                    jsonTeam.get("name").asString,
                                    memberIds
                                )
                                teams.add(team)
                                teamLiveData.value = teams
                            }
                        }
                    }

                }else{
                    teamLiveData.value = teams
                }
            }
        }
        return teamLiveData
    }
}