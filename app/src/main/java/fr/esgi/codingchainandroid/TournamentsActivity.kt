package fr.esgi.codingchainandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.esgi.codingchainandroid.api.turnaments.TurnamentModel
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
        val turnamentsService = TurnamentService(this.applicationContext)
        val turnaments: ArrayList<TurnamentModel> = ArrayList()
        turnamentsService.getPublishedTurnaments { response ->
        if (response != null){
            val items = response.get("result").asJsonArray
            Log.d("Reponse",items.toString());
            items.forEach{ item ->
                val result = item.asJsonObject
                val turnament = result.get("result")
                Log.d("Reponse",turnament.toString());

                val turnamentType = object : TypeToken<TurnamentModel>() {}.type

                turnaments.add(Gson().fromJson(turnament, turnamentType))
            }
            Log.d("Turnements",turnaments.toString())
        }

        }
    }

}