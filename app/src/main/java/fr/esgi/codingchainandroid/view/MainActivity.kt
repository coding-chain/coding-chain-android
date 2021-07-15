package fr.esgi.codingchainandroid.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.esgi.codingchainandroid.R
import fr.esgi.codingchainandroid.api.provider.AppPreferences

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity);
        AppPreferences.init(this)

        if(isConnected()){
            val intent = Intent(this, HomeActivity::class.java);
            startActivity(intent)
        }else{
            val intent = Intent(this, ConnectionActivity::class.java);
            startActivity(intent)
        }
    }

    private fun isConnected(): Boolean {
        return AppPreferences.token !== "" ;
    }
}