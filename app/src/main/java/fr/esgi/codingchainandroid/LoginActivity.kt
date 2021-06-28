package fr.esgi.codingchainandroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import fr.esgi.codingchainandroid.api.provider.AppPreferences
import fr.esgi.codingchainandroid.api.user.model.LoginModel
import fr.esgi.codingchainandroid.api.user.service.LoginService
import org.w3c.dom.Text

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        onClicks()
    }

    // todo manage errors

    private fun login() {
        val error = findViewById<TextView>(R.id.error)
        // TODO loader ?
        val emailLogin = findViewById<EditText>(R.id.email_login)
        val passwordLogin = findViewById<EditText>(R.id.login_password)
        val username = emailLogin.text.toString()
        val password = passwordLogin.text.toString()
        if (username.isNotEmpty() && password.isNotEmpty() && username.isNotBlank()
            && password.isNotBlank()) {
            error.visibility = View.INVISIBLE;
            val loginService = LoginService(this.applicationContext)

            var response: JsonObject?

            loginService.loginUser(LoginModel(username, password)) { loginResponse ->
                response = loginResponse
                if (response !== null) {
                    AppPreferences.isLogin = true
                    // todo check response
                    AppPreferences.token = response!!.get("token").toString()

                    val intent = Intent(this, HomeActivity::class.java);
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Login Failed ! ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }else {
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