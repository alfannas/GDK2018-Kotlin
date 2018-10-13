package com.walukustudio.kotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.walukustudio.kotlin.R
import com.walukustudio.kotlin.model.Team
import kotlinx.android.synthetic.main.item_team.view.*
import org.jetbrains.anko.*

class TeamAdapter (private val teams: List<Team>, private val clicklistener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        //return TeamViewHolder(SingleTeamUI().createView(AnkoContext.create(parent.context, parent)))
        return TeamViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false))
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teams[position],clicklistener)
    }

}

class SingleTeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = R.id.team_badge
                }.lparams{
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = R.id.team_name
                    textSize = 16f
                }.lparams{
                    margin = dip(15)
                }
            }
        }
    }
}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view){
    private val teamBadge: ImageView = view.team_badge
    private val teamName: TextView = view.team_name

    fun bindItem(teams: Team,clicklistener: (Team) -> Unit){
        if(teams.teamBadge != "")Picasso.get().load(teams.teamBadge).into(teamBadge)
        teamName.text = teams.teamName

        itemView.setOnClickListener { clicklistener(teams) }
    }
}