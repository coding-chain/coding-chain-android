package fr.esgi.codingchainandroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.gson.JsonObject
import fr.esgi.codingchainandroid.api.provider.AppPreferences
import fr.esgi.codingchainandroid.api.right.service.RightService
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
        val data = findViewById<LinearLayout>(R.id.data)
        val email = findViewById<TextView>(R.id.email)
        val username = findViewById<TextView>(R.id.username)
        val rights = findViewById<TextView>(R.id.rights)
        loader.visibility = View.VISIBLE
        val userService = UserService(this.applicationContext)

        var response: JsonObject?

        userService.getUser() { meResponse ->
            response = meResponse
            if (response !== null) {
                data.visibility = View.VISIBLE
                email.text = getString(R.string.account_email, response!!.get("email").asString)
                username.text = getString(R.string.account_username, response!!.get("username").asString)
                val jsonRightIds = response!!.get("rightIds").asJsonArray
                val rightIds: MutableList<String> = ArrayList()
                for (i in 0 until jsonRightIds.size()) {
                    rightIds.add(jsonRightIds.get(i).asString)
                }
                if(rightIds.isNotEmpty()){
                    val rightService = RightService(this.applicationContext)
                    var rightResponse: JsonObject?
                    var rightsTxt = "";
                    rightIds.forEach {
                        rightService.getOneById(it) { rResponse ->
                            rightResponse = rResponse!!.get("result").asJsonObject
                            if (rightResponse != null) {
                                rightsTxt += rightResponse!!.get("name").asString + " "
                                rights.text = getString(R.string.account_rights, rightsTxt)
                            }
                        }
                    }
                    rights.text = getString(R.string.account_rights, rightsTxt)
                }else {
                    rights.text = getString(R.string.account_rights, getString(R.string.none))
                }
            }
            loader.visibility = View.GONE
        }
    }

    private fun onClicks() {
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java);
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