package fr.esgi.codingchainandroid.view

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fr.esgi.codingchainandroid.R
import fr.esgi.codingchainandroid.api.provider.AppPreferences
import fr.esgi.codingchainandroid.api.user.model.LoginApiModel
import fr.esgi.codingchainandroid.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.login_activity.back_button
import kotlinx.android.synthetic.main.login_activity.error
import kotlinx.android.synthetic.main.login_activity.progress
import kotlinx.android.synthetic.main.register_activity.*

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

        val email = email_login.text.toString()
        val password = login_password.text.toString()
        if (!manageError(email, password)) {
            progress.visibility = View.VISIBLE
            login_submit.isEnabled = false
            error.visibility = View.GONE;

            loginViewModel.login(this, LoginApiModel(email, password))
                .observe(this, Observer {
                    if(it.error != "" && it.error != null){
                        error.text = it.error
                        error.visibility = View.VISIBLE
                    }else{
                        error.visibility = View.GONE
                        AppPreferences.token = it.token
                        val intent = Intent(this, HomeActivity::class.java);
                        startActivity(intent)
                    }
                    progress.visibility = View.INVISIBLE
                    register_submit.isEnabled = true
                })
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