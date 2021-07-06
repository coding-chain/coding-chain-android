package fr.esgi.codingchainandroid

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.gson.JsonObject
import fr.esgi.codingchainandroid.api.provider.AppPreferences
import fr.esgi.codingchainandroid.api.right.service.RightService
import fr.esgi.codingchainandroid.api.user.model.RegisterModel
import fr.esgi.codingchainandroid.api.user.model.UserModel
import fr.esgi.codingchainandroid.api.user.service.UserService

class AccountActivity : AppCompatActivity() {

    var user: UserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_activity)
        onClicks()
        fetchMe()
    }

    private fun fetchMe(){
        val loader = findViewById<ProgressBar>(R.id.progress)
        val email = findViewById<TextView>(R.id.email)
        val username = findViewById<TextView>(R.id.username)
        val rights = findViewById<TextView>(R.id.rights)
        loader.visibility = View.VISIBLE
        val userService = UserService(this.applicationContext)

        var response: JsonObject?

        userService.getUser() { meResponse ->
            response = meResponse
            if (response !== null) {
                email.text = response!!.get("email").asString
                username.text =  response!!.get("username").asString
                val jsonRightIds = response!!.get("rightIds").asJsonArray
                val rightIds: MutableList<String> = ArrayList()
                for (i in 0 until jsonRightIds.size()) {
                    rightIds.add(jsonRightIds.get(i).asString)
                }
                user = UserModel(response!!.get("id").asString,
                    response!!.get("email").asString,
                    response!!.get("username").asString,
                    rightIds)
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

    private fun updateMe() {
        val userService = UserService(this.applicationContext)
        val email = findViewById<TextView>(R.id.email)
        val error = findViewById<TextView>(R.id.error)
        val username = findViewById<TextView>(R.id.username)
        val password = findViewById<TextView>(R.id.password)
        val passwordConfirm = findViewById<TextView>(R.id.password_confirm)
        val loader = findViewById<ProgressBar>(R.id.progress)
        val modifyButton = findViewById<Button>(R.id.modify)

        val data = RegisterModel(null, null, null)
        var hasError = false

        if(password.text.toString() != "" && passwordConfirm.text.toString() != ""){
            if(password.text.toString() != passwordConfirm.text.toString()){
                error.text = getString(R.string.different_passwords)
                hasError = true
            }else{
                data.password = password.text.toString()
            }
        }
        if(user != null && user!!.email != email.text.toString()){
            if(Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
                data.email = email.text.toString()
            }else {
                error.text = getString(R.string.wrong_email)
                hasError = true
            }
        }
        if(user != null && user!!.username != username.text.toString()){
            data.username = username.text.toString()
        }
        if(hasError){
            error.visibility = View.VISIBLE
        }else{
            modifyButton.isEnabled = false
            loader.visibility = View.VISIBLE
            userService.updateMe(data) { response ->
                if(response != null){
                    error.visibility = View.GONE
                    Toast.makeText(this, getText(R.string.modify_success), Toast.LENGTH_LONG).show();
                }else{
                    error.visibility = View.VISIBLE
                    error.text = getString(R.string.an_error_occured_during_modify)
                }
                loader.visibility = View.GONE
                modifyButton.isEnabled = true
            }
        }
    }

    private fun onClicks() {
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java);
            startActivity(intent)
        }
        val modifyButton = findViewById<Button>(R.id.modify)
        modifyButton.setOnClickListener {
            updateMe()
        }
    }

}