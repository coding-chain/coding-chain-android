package fr.esgi.codingchainandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ConnectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection_activity);
        onClicks()
    }

    private fun login (){
        // TODO call to connect
        // if success go to home
        val intent = Intent(this, HomeActivity::class.java);
        startActivity(intent)
    }

    private fun onClicks(){
        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            login()
        }

        val registerButton = findViewById<Button>(R.id.register_button)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java);
            startActivity(intent)
        }
    }

}