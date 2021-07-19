package fr.esgi.codingchainandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.esgi.codingchainandroid.adapters.TurnamentsAdapater
import fr.esgi.codingchainandroid.api.turnaments.model.TurnamentModel
import fr.esgi.codingchainandroid.api.turnaments.service.TurnamentService

class TournamentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.turnaments_activity)
        onClicks()
        fetchTurnaments()
    }

    private fun onClicks() {
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java);
            startActivity(intent)
        }
    }

    private fun fetchTurnaments() {
        val loader = findViewById<ProgressBar>(R.id.progress)
        val turnamentList = findViewById<ListView>(R.id.turnament_list)
        val noResult = findViewById<TextView>(R.id.no_result)
        loader.visibility = View.VISIBLE
        val turnamentsService = TurnamentService(this.applicationContext)
        val turnaments: ArrayList<TurnamentModel> = ArrayList()
        val adapter = TurnamentsAdapater(this, turnaments)

        turnamentList.adapter = adapter
        turnamentsService.getPublishedTurnaments { response ->
            if (response != null) {
                val items = response.get("result").asJsonArray
                Log.d("Reponse", items.toString());
                items.forEach { item ->
                    val result = item.asJsonObject
                    val turnament = result.get("result")
                    Log.d("Reponse", turnament.toString());

                    val turnamentType = object : TypeToken<TurnamentModel>() {}.type

                    turnaments.add(Gson().fromJson(turnament, turnamentType))
                    adapter.notifyDataSetChanged()
                }
                loader.visibility = View.GONE

                Log.d("Turnements", turnaments.toString())
            }
            else{
                noResult.visibility = View.VISIBLE
                loader.visibility = View.GONE
            }

        }
    }

}