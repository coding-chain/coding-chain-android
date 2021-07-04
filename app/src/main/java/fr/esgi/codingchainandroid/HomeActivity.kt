package fr.esgi.codingchainandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import fr.esgi.codingchainandroid.api.provider.AppPreferences

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        onClicks()
    }

    private fun onClicks() {
        val tournamentsButton = findViewById<Button>(R.id.tournaments)
        tournamentsButton.setOnClickListener {
            val intent = Intent(this, TournamentsActivity::class.java);
            startActivity(intent)
        }
        val teamButton = findViewById<Button>(R.id.teams)
        teamButton.setOnClickListener {
            val intent = Intent(this, TeamActivity::class.java);
            startActivity(intent)
        }
        val accountButton = findViewById<Button>(R.id.account)
        accountButton.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java);
            startActivity(intent)
        }
        val logoutButton = findViewById<Button>(R.id.logout)
        logoutButton.setOnClickListener {
            AppPreferences.token = ""
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent)
        }

    }

}