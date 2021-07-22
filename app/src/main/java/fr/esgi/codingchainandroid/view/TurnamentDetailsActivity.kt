package fr.esgi.codingchainandroid.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.esgi.codingchainandroid.R
import fr.esgi.codingchainandroid.adapters.TurnamentLeaderBoardAdapter
import fr.esgi.codingchainandroid.api.turnaments.model.TurnamentLeaderBoardApiModel
import fr.esgi.codingchainandroid.api.turnaments.model.TurnamentApiModel
import fr.esgi.codingchainandroid.model.TournamentLeaderBoardModel
import fr.esgi.codingchainandroid.viewmodel.TournamentDetailsViewModel
import kotlinx.android.synthetic.main.turnament_details.*
import kotlinx.android.synthetic.main.turnament_details.back_button
import kotlinx.android.synthetic.main.turnament_details.no_result
import kotlinx.android.synthetic.main.turnament_details.progress

class TurnamentDetailsActivity : AppCompatActivity(), LayoutInflater.Factory {
    lateinit var turnamentApi: TurnamentApiModel
    lateinit var tournamentDetailsViewModel: TournamentDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.turnament_details)
        tournamentDetailsViewModel = ViewModelProvider(this).get(TournamentDetailsViewModel::class.java)
        onClicks()
        val turnamentType = object : TypeToken<TurnamentApiModel>() {}.type
        turnamentApi = Gson().fromJson(this.intent.getStringExtra("turnament"), turnamentType)
        populateView()
        fetchLeaderBoard()
    }

    private fun onClicks() {
        back_button.setOnClickListener {
            val intent = Intent(this, TournamentsActivity::class.java);
            startActivity(intent)
        }
    }

    private fun fetchLeaderBoard() {
        progress.visibility = View.VISIBLE
        val leaderBoardApi: ArrayList<TournamentLeaderBoardModel> = ArrayList()
        val adapter = TurnamentLeaderBoardAdapter(this,leaderBoardApi)
        leaderboard_list.adapter = adapter

        tournamentDetailsViewModel.getLeaderBoard(this.applicationContext, this.turnamentApi.id)
            .observe(this, Observer { result ->

            if(result != null){
                leaderBoardApi.clear()
                leaderBoardApi.addAll(result)
                adapter.notifyDataSetChanged()
            }else{
                no_result.visibility = View.VISIBLE
            }
            progress.visibility = View.GONE
        })
    }

    private fun populateView() {
        turnament_name.text = turnamentApi.name
        turnament_subtitle.text = stripHtml(turnamentApi.description)
        turnament_steps.text = turnamentApi.stepsIds.size.toString()
    }

    @SuppressLint("NewApi")
    private fun stripHtml(html: String): String {
        var mode = Html.FROM_HTML_MODE_COMPACT
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mode = Html.FROM_HTML_MODE_LEGACY
        }
        return Html.fromHtml(html,mode).toString();
    }

}