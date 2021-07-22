package fr.esgi.codingchainandroid.view

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fr.esgi.codingchainandroid.R
import fr.esgi.codingchainandroid.api.user.model.RegisterApiModel
import fr.esgi.codingchainandroid.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.register_activity.*
import kotlinx.android.synthetic.main.register_activity.back_button
import kotlinx.android.synthetic.main.register_activity.error
import kotlinx.android.synthetic.main.register_activity.progress

class RegisterActivity: AppCompatActivity() {
    lateinit var registerViewModel: RegisterViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity);
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        onClicks()
    }


    private fun manageError(email: String, password: String,
                            passwordConfirm: String, username:String): Boolean {
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
        val email = email_input.text.toString()
        val password = password_input.text.toString()
        val passwordConfirm = confirm_password_input.text.toString()
        val username = username_input.text.toString()
        if(!manageError(email, password, passwordConfirm, username)){
            progress.visibility = View.VISIBLE
            register_submit.isEnabled = false
            error.visibility = View.INVISIBLE;

            registerViewModel.register(this, RegisterApiModel(email, password, username))
                ?.observe(this, Observer {
                    if(it != "" && it != null){
                        error.text = it
                        error.visibility = View.VISIBLE
                    }else{
                        error.visibility = View.GONE
                        val intent = Intent(this, LoginActivity::class.java);
                        startActivity(intent)
                    }
                    error.text = it
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

        register_submit.setOnClickListener {
            register();
        }
    }
}