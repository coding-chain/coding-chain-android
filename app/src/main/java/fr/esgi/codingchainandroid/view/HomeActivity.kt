package fr.esgi.codingchainandroid.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.esgi.codingchainandroid.R
import fr.esgi.codingchainandroid.api.provider.AppPreferences
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        onClicks()
    }

    private fun onClicks() {
        tournaments.setOnClickListener {
            val intent = Intent(this, TournamentsActivity::class.java);
            startActivity(intent)
        }
        teams.setOnClickListener {
            val intent = Intent(this, TeamActivity::class.java);
            startActivity(intent)
        }
        account.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java);
            startActivity(intent)
        }
        logout.setOnClickListener {
            AppPreferences.token = ""
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent)
        }

    }

}