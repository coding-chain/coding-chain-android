package fr.esgi.codingchainandroid

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import fr.esgi.codingchainandroid.api.provider.AppPreferences
import fr.esgi.codingchainandroid.api.user.model.LoginModel
import fr.esgi.codingchainandroid.api.user.service.LoginService

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        onClicks()
    }

    private fun manageError(email: String, password: String): Boolean {
        val error = findViewById<TextView>(R.id.error)
        var hasError = false;
        if (email.isEmpty() || password.isEmpty() || email.isBlank()
            || password.isBlank()) {
                error.text = getText(R.string.fill_fields)
            hasError = true;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            error.text = getText(R.string.wrong_email)
            hasError = true;
        }else{
            error.visibility = View.GONE;
        }
        return hasError;
    }

    private fun login() {
        val loginButton = findViewById<Button>(R.id.login_submit)
        val loader = findViewById<ProgressBar>(R.id.progress)
        val error = findViewById<TextView>(R.id.error)
        val emailLogin = findViewById<EditText>(R.id.email_login)
        val passwordLogin = findViewById<EditText>(R.id.login_password)
        val email = emailLogin.text.toString()
        val password = passwordLogin.text.toString()
        if (!manageError(email, password)) {
            loader.visibility = View.VISIBLE
            loginButton.isEnabled = false
            error.visibility = View.GONE;
            val loginService = LoginService(this.applicationContext)

            var response: JsonObject?

            loginService.loginUser(LoginModel(email, password)) { loginResponse ->
                response = loginResponse
                if (response !== null) {
                    AppPreferences.token = response!!.get("token").asString

                    val intent = Intent(this, HomeActivity::class.java);
                    startActivity(intent)
                } else {
                    error.text = getText(R.string.login_error)
                    error.visibility = View.GONE;
                }
                loader.visibility = View.INVISIBLE
                loginButton.isEnabled = true
            }
        }else{
            error.visibility = View.VISIBLE;
        }

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