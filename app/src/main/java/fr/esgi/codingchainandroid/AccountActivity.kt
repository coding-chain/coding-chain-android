package fr.esgi.codingchainandroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import fr.esgi.codingchainandroid.api.provider.AppPreferences
import fr.esgi.codingchainandroid.api.user.model.LoginModel
import fr.esgi.codingchainandroid.api.user.model.UserModel
import fr.esgi.codingchainandroid.api.user.service.LoginService
import fr.esgi.codingchainandroid.api.user.service.UserService

class AccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_activity)
        onClicks()
        fetchMe()
    }

    private fun fetchMe(){
        val loader = findViewById<ProgressBar>(R.id.progress)
        loader.visibility = View.VISIBLE
        val userService = UserService(this.applicationContext)

        var response: JsonObject?

        userService.getUser() { meResponse ->
            response = meResponse
            if (response !== null) {
                // TODO display data
            }
            loader.visibility = View.INVISIBLE
        }
    }

    private fun onClicks() {
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java);
            startActivity(intent)
        }
    }

}