package fr.esgi.codingchainandroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import fr.esgi.codingchainandroid.adapters.TeamAdapter
import fr.esgi.codingchainandroid.api.right.service.RightService
import fr.esgi.codingchainandroid.api.team.model.TeamModel
import fr.esgi.codingchainandroid.api.team.service.TeamService
import fr.esgi.codingchainandroid.api.user.service.UserService

class TeamActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.team_activity)
        onClicks()
        fetchMyTeams()
    }

    private fun onClicks() {
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java);
            startActivity(intent)
        }
    }

    private fun fetchMyTeams(){
        val loader = findViewById<ProgressBar>(R.id.progress)
        val data = findViewById<LinearLayout>(R.id.data)
        val teamList = findViewById<ListView>(R.id.team_list)
        val noResult = findViewById<TextView>(R.id.no_result)
        loader.visibility = View.VISIBLE
        val teamService = TeamService(this.applicationContext)
        val meService = UserService(this.applicationContext)
        val teams: ArrayList<TeamModel> = ArrayList()

        val adapter = TeamAdapter(this, teams)
        teamList.adapter = adapter

        meService.getUser { meResponse ->
            if (meResponse !== null) {
                val jsonTeamIds = meResponse.get("teamIds").asJsonArray
                val teamIds: MutableList<String> = ArrayList()
                jsonTeamIds.forEach { jsonTeamId -> teamIds.add(jsonTeamId.asString) }
                if(teamIds.size > 0){
                    noResult.visibility = View.GONE
                    var counter = 0;
                    teamIds.forEach{
                        counter ++ ;
                        teamService.getOneById(it) { teamResponse ->
                            if (teamResponse !== null) {
                                data.visibility = View.VISIBLE
                                val jsonTeam = teamResponse.get("result").asJsonObject
                                val jsonMemberIds = jsonTeam.get("membersIds").asJsonArray
                                val memberIds: MutableList<String> = ArrayList()
                                jsonMemberIds.forEach{ jsonMemberId ->
                                    memberIds.add(jsonMemberId.asString)
                                }
                                val team = TeamModel(jsonTeam.get("id").asString,
                                    jsonTeam.get("name").asString,
                                    memberIds
                                )
                                teams.add(team)
                                adapter.notifyDataSetChanged()
                            }
                            if(counter == teamIds.size){
                                loader.visibility = View.GONE
                            }
                        }
                    }

                }else{
                    data.visibility = View.GONE
                    noResult.visibility = View.VISIBLE
                    loader.visibility = View.GONE
                }
            }
        }
    }
}