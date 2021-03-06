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
import fr.esgi.codingchainandroid.model.RegisterModel
import fr.esgi.codingchainandroid.model.UserModel
import fr.esgi.codingchainandroid.viewmodel.AccountViewModel
import kotlinx.android.synthetic.main.account_activity.*

class AccountActivity : AppCompatActivity() {

    var user: UserModel? = null
    lateinit var userViewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_activity)
        userViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        user = UserModel(null, null, null, null)
        onClicks()
        fetchMe()
    }

    private fun fetchMe(){
        progress.visibility = View.VISIBLE

        userViewModel.getUser(this.applicationContext)!!.observe(this, Observer { result ->
            progress.visibility = View.VISIBLE
            if (result.rights?.isNotEmpty() == true) {
                var rightsStr = ""
                result.rights!!.forEach{
                    rightsStr += it.name + " "
                }
                rights.text = getString(R.string.account_rights, rightsStr)
            } else {
                rights.text = getString(R.string.account_rights, getString(R.string.none))
            }
            user!!.email = result.email
            user!!.username = result.username
            email.setText(result.email)
            username.setText(result.username)
            progress.visibility = View.GONE
        })
    }

    private fun updateMe() {
        val data = RegisterModel(null, null, null, null,null)
        var hasError = false

        if(password.text.toString() != "" && password_confirm.text.toString() != ""){
            if(password.text.toString() != password_confirm.text.toString()){
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
            modify.isEnabled = false
            progress.visibility = View.VISIBLE
            userViewModel.updateMe(this.applicationContext, data)!!.observe(this, Observer { result ->
                progress.visibility = View.VISIBLE
                if(result == "SUCCESS"){
                    error.visibility = View.GONE
                    Toast.makeText(this, getText(R.string.modify_success), Toast.LENGTH_LONG).show();
                }else if(result == "ERROR"){
                    error.visibility = View.VISIBLE
                    error.text = getString(R.string.an_error_occured_during_modify)
                }
                progress.visibility = View.GONE
                modify.isEnabled = true
            })
        }
    }

    private fun onClicks() {
        back_button.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java);
            startActivity(intent)
        }
        modify.setOnClickListener {
            updateMe()
        }
    }

}