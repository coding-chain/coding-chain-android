package fr.esgi.codingchainandroid.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fr.esgi.codingchainandroid.R
import fr.esgi.codingchainandroid.adapters.TeamAdapter
import fr.esgi.codingchainandroid.model.TeamModel
import fr.esgi.codingchainandroid.viewmodel.TeamViewModel
import kotlinx.android.synthetic.main.team_activity.*

class TeamActivity : AppCompatActivity() {

    lateinit var teamViewModel: TeamViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.team_activity)
        teamViewModel = ViewModelProvider(this).get(TeamViewModel::class.java)
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
        val teams: ArrayList<TeamModel> = ArrayList()

        val adapter = TeamAdapter(this, teams)
        team_list.adapter = adapter

        teamViewModel.getMyTeam(this.applicationContext)!!.observe(this, Observer { t ->
            progress.visibility = View.VISIBLE
            if (t.size > 0) {
                data.visibility = View.VISIBLE
                no_result.visibility = View.GONE
                teams.clear()
                teams.addAll(t)
                adapter.notifyDataSetChanged()
            } else {
                data.visibility = View.GONE
                no_result.visibility = View.VISIBLE
            }
            progress.visibility = View.GONE
        })

    }
}