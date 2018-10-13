package com.walukustudio.kotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.walukustudio.kotlin.R
import com.walukustudio.kotlin.model.FavoriteTeam
import kotlinx.android.synthetic.main.item_team.view.*

class FavoriteTeamAdapter (private val context: Context, private val teams: List<FavoriteTeam>, private val clicklistener: (FavoriteTeam) -> Unit)
    : RecyclerView.Adapter<FavTeamViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavTeamViewHolder {
        return FavTeamViewHolder(LayoutInflater.from(context).inflate(R.layout.item_team, parent, false))
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onBindViewHolder(holder: FavTeamViewHolder, position: Int) {
        holder.bindItem(teams[position],clicklistener)
    }
}

class FavTeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val teamBadge: ImageView = view.team_badge
    private val teamName: TextView = view.team_name

    fun bindItem(teams : FavoriteTeam, clicklistener: (FavoriteTeam) -> Unit) {

        if(teams.teamBadge != "") Picasso.get().load(teams.teamBadge).into(teamBadge)
        teamName.text = teams.teamName

        itemView.setOnClickListener { clicklistener(teams) }
    }
}