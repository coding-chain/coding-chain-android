package fr.esgi.codingchainandroid

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.esgi.codingchainandroid.api.turnaments.TurnamentModel
import org.w3c.dom.Text

class TurnamentViewActivity : AppCompatActivity(), LayoutInflater.Factory {
    lateinit var turnament: TurnamentModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.turnament_view)
        onClicks()
        val turnamentType = object : TypeToken<TurnamentModel>() {}.type
        turnament = Gson().fromJson(this.intent.getStringExtra("turnament"), turnamentType)
        populateView()
        Log.d("Turnament", turnament.toString())
    }
    private fun onClicks() {
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java);
            startActivity(intent)
        }
    }



    private fun populateView() {
        val turnamentName = findViewById<TextView>(R.id.turnament_name)
        val turnamentSubtitle = findViewById<TextView>(R.id.turnament_subtitle)
        val steps = findViewById<TextView>(R.id.turnament_steps)

        turnamentName.text = turnament.name
        turnamentSubtitle.text = stripHtml(turnament.description)
        steps.text = turnament.stepsIds.size.toString()

    }

    @SuppressLint("NewApi")
    private fun stripHtml(html: String): String {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT).toString();
        }
    }

}