package fr.esgi.codingchainandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity);

        onClicks()
    }


    private fun manageError(email: String, password: String,
                            passwordConfirm: String, firstName:String, lastName:String): Boolean {
        val error = findViewById<TextView>(R.id.error)
        var hasError = false;
        if (email.isEmpty() || password.isEmpty() || email.isBlank()
            || password.isBlank() || passwordConfirm.isEmpty() || passwordConfirm.isBlank()
            || firstName.isEmpty() || firstName.isBlank()|| lastName.isEmpty() || lastName.isBlank()) {
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
        val emailInput = findViewById<EditText>(R.id.email_input)
        val passwordInput = findViewById<EditText>(R.id.password_input)
        val passwordConfirmInput = findViewById<EditText>(R.id.confirm_password_input)
        val firstNameInput = findViewById<EditText>(R.id.firstname_input)
        val lastNameInput = findViewById<EditText>(R.id.lastname_input)
        val loader = findViewById<ProgressBar>(R.id.progress)
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()
        val passwordConfirm = passwordConfirmInput.text.toString()
        val firstName = firstNameInput.text.toString()
        val lastName = lastNameInput.text.toString()
        if(manageError(email, password, passwordConfirm, firstName, lastName)){
            loader.visibility = View.VISIBLE
            // TODO call
            val intent = Intent(this, HomeActivity::class.java);
            startActivity(intent)
            loader.visibility = View.INVISIBLE
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