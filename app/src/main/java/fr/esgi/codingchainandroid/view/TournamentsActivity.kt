package fr.esgi.codingchainandroid.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.esgi.codingchainandroid.adapters.TurnamentsAdapater
import fr.esgi.codingchainandroid.api.turnaments.model.TurnamentModel
import fr.esgi.codingchainandroid.api.turnaments.service.TurnamentService
import fr.esgi.codingchainandroid.R
import kotlinx.android.synthetic.main.turnaments_activity.*

class TournamentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.turnaments_activity)
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
        val turnamentsService = TurnamentService(this.applicationContext)
        val turnaments: ArrayList<TurnamentModel> = ArrayList()
        val adapter = TurnamentsAdapater(this, turnaments)

        turnament_list.adapter = adapter
        turnamentsService.getPublishedTurnaments { response ->
            if (response != null) {
                val items = response.get("result").asJsonArray
                items.forEach { item ->
                    val result = item.asJsonObject
                    val turnament = result.get("result")
                    val turnamentType = object : TypeToken<TurnamentModel>() {}.type
                    turnaments.add(Gson().fromJson(turnament, turnamentType))
                    adapter.notifyDataSetChanged()
                }
                progress.visibility = View.GONE
            }
            else{
                no_result.visibility = View.VISIBLE
                progress.visibility = View.GONE
            }

        }
    }

}