package fr.esgi.codingchainandroid.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.esgi.codingchainandroid.R
import fr.esgi.codingchainandroid.adapters.TurnamentLeaderBoardAdapter
import fr.esgi.codingchainandroid.api.turnaments.model.TurnamentLeaderBoardModel
import fr.esgi.codingchainandroid.api.turnaments.model.TurnamentModel
import fr.esgi.codingchainandroid.api.turnaments.service.TurnamentService
import kotlinx.android.synthetic.main.turnament_view.*

class TurnamentViewActivity : AppCompatActivity(), LayoutInflater.Factory {
    lateinit var turnament: TurnamentModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.turnament_view)
        onClicks()
        val turnamentType = object : TypeToken<TurnamentModel>() {}.type
        turnament = Gson().fromJson(this.intent.getStringExtra("turnament"), turnamentType)
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
        val turnamentsService = TurnamentService(this.applicationContext)
        val leaderBoard: ArrayList<TurnamentLeaderBoardModel> = ArrayList()
        val adapter = TurnamentLeaderBoardAdapter(this,leaderBoard)
        leaderboard_list.adapter = adapter
        turnamentsService.getTurnamentLeaderBoard(this.turnament.id){ response ->
            if(response != null){
                val items = response.get("result").asJsonArray
                items.forEach { item ->
                    val result = item.asJsonObject
                    val leaderboardItem = result.get("result")
                    val leaderboardType = object : TypeToken<TurnamentLeaderBoardModel>() {}.type
                    leaderBoard.add(Gson().fromJson(leaderboardItem,leaderboardType))
                    adapter.notifyDataSetChanged()
                }
                progress.visibility = View.GONE
            }
            else {
                no_result.visibility = View.VISIBLE
                progress.visibility = View.GONE
            }
        }
    }


    private fun populateView() {
        turnament_name.text = turnament.name
        turnament_subtitle.text = stripHtml(turnament.description)
        turnament_steps.text = turnament.stepsIds.size.toString()
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