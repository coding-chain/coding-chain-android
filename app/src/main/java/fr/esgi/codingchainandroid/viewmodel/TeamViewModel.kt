package fr.esgi.codingchainandroid.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.esgi.codingchainandroid.api.team.model.TeamApiModel
import fr.esgi.codingchainandroid.model.TeamModel
import fr.esgi.codingchainandroid.api.team.service.TeamService
import fr.esgi.codingchainandroid.api.turnaments.model.TurnamentApiModel
import fr.esgi.codingchainandroid.api.user.service.UserService
import fr.esgi.codingchainandroid.model.TournamentModel

class TeamViewModel : ViewModel() {

    var teamLiveData = MutableLiveData<ArrayList<TeamModel>>()

    fun getMyTeam(context: Context): LiveData<ArrayList<TeamModel>>? {
        val teams: ArrayList<TeamApiModel> = ArrayList()
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
                                val team = TeamApiModel(
                                    jsonTeam.get("id").asString,
                                    jsonTeam.get("name").asString,
                                    memberIds
                                )
                                teams.add(team)
                                teamLiveData.value = toTeam(teams)
                            }
                        }
                    }

                }else{
                    teamLiveData.value = toTeam(teams)
                }
            }
        }
        return teamLiveData
    }

    fun toTeam(input: ArrayList<TeamApiModel>): ArrayList<TeamModel>{
        val output = ArrayList<TeamModel>()
        input.forEach{
            output.add(toTeamModel(it))
        }
        return output
    }

    private fun toTeamModel (model : TeamApiModel): TeamModel {
        return TeamModel(model.id, model.name, model.membersIds)
    }

}