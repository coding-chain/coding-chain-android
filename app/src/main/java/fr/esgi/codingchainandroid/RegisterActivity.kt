package fr.esgi.codingchainandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity);

        onClicks()
    }

    // todo manage errors

    private fun register() {
        // todo call register
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

        val registerButton = findViewById<Button>(R.id.register_submit)
        registerButton.setOnClickListener {
            register();
        }
    }
}