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
import fr.esgi.codingchainandroid.TurnamentViewActivity
import fr.esgi.codingchainandroid.api.turnaments.model.TurnamentLeaderBoardModel
import fr.esgi.codingchainandroid.api.turnaments.model.TurnamentModel
import org.w3c.dom.Text

class TurnamentLeaderBoardAdapter(
    private val context: Context,
    private val dataSource: ArrayList<TurnamentLeaderBoardModel>
) :
    BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): TurnamentLeaderBoardModel {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getItemPosition(itemId: String): Int {
        val dataSourceIds = dataSource.map { leaderboard -> leaderboard.id }
        return dataSourceIds.indexOf(itemId) + 1
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(R.layout.tunament_view_ranking_item, parent, false)

            holder = ViewHolder()
            holder.name = view.findViewById(R.id.team_name) as TextView
            holder.score = view.findViewById(R.id.team_score) as TextView
            holder.position = view.findViewById(R.id.team_position) as TextView
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val leaderBoard = getItem(position)

        val name = holder.name
        val score = holder.score
        val position = holder.position

        name.text = leaderBoard.name
        score.text = leaderBoard.score.toString()
        position.text = getItemPosition(leaderBoard.id).toString()
        return view
    }

    private class ViewHolder {
        lateinit var name: TextView
        lateinit var score: TextView
        lateinit var position: TextView
    }
}