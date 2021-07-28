package fr.esgi.codingchainandroid.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import fr.esgi.codingchainandroid.R
import fr.esgi.codingchainandroid.model.TeamModel

class TeamAdapter(private val context: Context,
                      private val dataSource: ArrayList<TeamModel>) : BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): TeamModel {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(R.layout.team_item, parent, false)

            holder = ViewHolder()
            holder.name = view.findViewById(R.id.name) as TextView
            holder.members = view.findViewById(R.id.members) as TextView
            view.tag = holder
        }else{
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val team = getItem(position)

        val name = holder.name
        val members = holder.members

        name.text = team.name
        members.text = context.getString(R.string.team_members_count , team.membersIds.size)
        return view
    }

    private class ViewHolder {
        lateinit var name: TextView
        lateinit var members: TextView
    }
}