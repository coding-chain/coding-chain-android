package fr.esgi.codingchainandroid.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.esgi.codingchainandroid.adapters.TurnamentsAdapater
import fr.esgi.codingchainandroid.api.turnaments.model.TurnamentModel
import fr.esgi.codingchainandroid.api.turnaments.service.TurnamentService
import fr.esgi.codingchainandroid.R
import fr.esgi.codingchainandroid.viewmodel.TournamentViewModel
import kotlinx.android.synthetic.main.account_activity.*
import kotlinx.android.synthetic.main.turnaments_activity.*
import kotlinx.android.synthetic.main.turnaments_activity.back_button
import kotlinx.android.synthetic.main.turnaments_activity.progress

class TournamentsActivity : AppCompatActivity() {
    lateinit var tournamentViewModel: TournamentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.turnaments_activity)
        tournamentViewModel = ViewModelProvider(this).get(TournamentViewModel::class.java)
        onClicks()
        fetchTurnaments()
    }

    private fun onClicks() {
        back_button.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java);
            startActivity(intent)
        }
    }

    private fun fetchTurnaments() {
        progress.visibility = View.VISIBLE
        val turnaments: ArrayList<TurnamentModel> = ArrayList()
        val adapter = TurnamentsAdapater(this, turnaments)

        turnament_list.adapter = adapter
        progress.visibility = View.VISIBLE

        tournamentViewModel.getTournaments(this.applicationContext)!!.observe(this, Observer { result ->

            if(result != null){
                turnaments.clear()
                turnaments.addAll(result)
                adapter.notifyDataSetChanged()
            }else{
                no_result.visibility = View.VISIBLE
            }
            progress.visibility = View.GONE
        })
    }

}