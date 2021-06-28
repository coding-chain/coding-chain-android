package fr.esgi.codingchainandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity);

        if(isConnected()){
            val intent = Intent(this, HomeActivity::class.java);
            startActivity(intent)
        }else{
            val intent = Intent(this, ConnectionActivity::class.java);
            startActivity(intent)
        }
    }

    private fun isConnected(): Boolean {
        // TODO check if has a connexion token
        return false;
    }
}