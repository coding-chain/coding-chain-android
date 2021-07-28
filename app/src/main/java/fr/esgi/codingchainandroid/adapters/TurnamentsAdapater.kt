package fr.esgi.codingchainandroid.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.google.gson.Gson
import fr.esgi.codingchainandroid.R
import fr.esgi.codingchainandroid.view.TurnamentDetailsActivity
import fr.esgi.codingchainandroid.model.TournamentModel


class TurnamentsAdapater(
    private val context: Context,
    private val dataSource: ArrayList<TournamentModel>
) :
    BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): TournamentModel {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(R.layout.turnament_item, parent, false)

            holder = ViewHolder()
            holder.name = view.findViewById(R.id.turnament_name) as TextView
            holder.participations = view.findViewById(R.id.participent_number) as TextView
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val turnament = getItem(position)

        view.setOnClickListener {
            val intent = Intent(context, TurnamentDetailsActivity::class.java)
            intent.putExtra("turnament", Gson().toJson(turnament))
            context.startActivity(intent)
        }

        val name = holder.name
        val participation = holder.participations

        name.text = turnament.name
        participation.text = turnament.participationsIds.size.toString()
        return view
    }

    private class ViewHolder {
        lateinit var name: TextView
        lateinit var participations: TextView
    }
}