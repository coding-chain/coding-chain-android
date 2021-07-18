package fr.esgi.codingchainandroid.view

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import fr.esgi.codingchainandroid.R
import fr.esgi.codingchainandroid.model.RegisterModel
import fr.esgi.codingchainandroid.api.user.service.RegisterService

class RegisterActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity);

        onClicks()
    }


    private fun manageError(email: String, password: String,
                            passwordConfirm: String, username:String): Boolean {
        val error = findViewById<TextView>(R.id.error)
        var hasError = false;
        if (email.isEmpty() || password.isEmpty() || email.isBlank()
            || password.isBlank() || passwordConfirm.isEmpty() || passwordConfirm.isBlank()
            || username.isEmpty() || username.isBlank()) {
            error.text = getText(R.string.fill_fields)
            hasError = true;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            error.text = getText(R.string.wrong_email)
            hasError = true;
        }else if(!password.equals(passwordConfirm)){
            error.text = getText(R.string.different_passwords)
            hasError = true;
        }
        else{
            error.visibility = View.INVISIBLE;
        }
        return hasError;
    }

    private fun register() {
        val error = findViewById<TextView>(R.id.error)
        val registerButton = findViewById<Button>(R.id.register_submit)
        val emailInput = findViewById<EditText>(R.id.email_input)
        val passwordInput = findViewById<EditText>(R.id.password_input)
        val passwordConfirmInput = findViewById<EditText>(R.id.confirm_password_input)
        val usernameInput = findViewById<EditText>(R.id.username_input)
        val loader = findViewById<ProgressBar>(R.id.progress)
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()
        val passwordConfirm = passwordConfirmInput.text.toString()
        val username = usernameInput.text.toString()
        if(!manageError(email, password, passwordConfirm, username)){
            loader.visibility = View.VISIBLE
            registerButton.isEnabled = false
            error.visibility = View.INVISIBLE;
            val registerService = RegisterService(this.applicationContext)

            var response: JsonObject?

            registerService.register(RegisterModel(email, password, username)) {
                    registerResponse ->
                response = registerResponse
                if (response !== null) {
                    val intent = Intent(this, LoginActivity::class.java);
                    startActivity(intent)
                } else {
                    error.text = getText(R.string.register_error)
                    error.visibility = View.VISIBLE;
                }
                loader.visibility = View.INVISIBLE
                registerButton.isEnabled = true
            }
            loader.visibility = View.INVISIBLE
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

        val registerButton = findViewById<Button>(R.id.register_submit)
        registerButton.setOnClickListener {
            register();
        }
    }
}