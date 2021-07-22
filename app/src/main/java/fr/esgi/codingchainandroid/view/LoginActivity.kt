package fr.esgi.codingchainandroid.view

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import fr.esgi.codingchainandroid.R
import fr.esgi.codingchainandroid.api.provider.AppPreferences
import fr.esgi.codingchainandroid.api.user.service.LoginService
import fr.esgi.codingchainandroid.model.LoginModel
import fr.esgi.codingchainandroid.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity : AppCompatActivity() {

    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        onClicks()
    }

    private fun manageError(email: String, password: String): Boolean {
        var hasError = false;
        if (email.isEmpty() || password.isEmpty() || email.isBlank()
            || password.isBlank()) {
                loginViewModel.errorLiveData.observe(this, Observer {
                    error.text = getText(R.string.fill_fields)
                })
            hasError = true;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loginViewModel.errorLiveData.observe(this, Observer {
                error.text = getText(R.string.wrong_email)
            })
            hasError = true;
        }else{
            error.visibility = View.GONE;
        }
        return hasError;
    }

    private fun login() {

        val email = email_login.text.toString()
        val password = login_password.text.toString()
        if (!manageError(email, password)) {
            progress.visibility = View.VISIBLE
            login_submit.isEnabled = false
            error.visibility = View.GONE;
            val loginService = LoginService(this.applicationContext)

            var response: JsonObject?

            loginService.loginUser(LoginModel(email, password, "")) { loginResponse ->
                response = loginResponse
                if (response !== null) {
                    AppPreferences.token = response!!.get("token").asString

                    val intent = Intent(this, HomeActivity::class.java);
                    startActivity(intent)
                } else {
                    loginViewModel.errorLiveData.observe(this, Observer {
                        error.text = getText(R.string.login_error)
                    })
                    error.visibility = View.GONE;

                }
                progress.visibility = View.INVISIBLE
                login_submit.isEnabled = true
            }
        }else{
            error.visibility = View.VISIBLE;
        }

    }

    private fun onClicks() {
        back_button.setOnClickListener {
            val intent = Intent(this, ConnectionActivity::class.java);
            startActivity(intent)
        }

        val loginButton = findViewById<Button>(R.id.login_submit)
        loginButton.setOnClickListener {
            login();
        }
    }
}