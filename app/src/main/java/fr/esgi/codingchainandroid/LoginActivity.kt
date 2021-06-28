package fr.esgi.codingchainandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        onClicks()
    }

    // todo manage errors

    private fun login() {
        // todo call login
        // then redirect to home
        val intent = Intent(this, HomeActivity::class.java);
        startActivity(intent)
    }

    private fun onClicks() {
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, ConnectionActivity::class.java);
            startActivity(intent)
        }

        val loginButton = findViewById<Button>(R.id.login_submit)
        loginButton.setOnClickListener {
            login();
        }
    }
}