package com.walukustudio.kotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.walukustudio.kotlin.R
import com.walukustudio.kotlin.model.Player
import com.walukustudio.kotlin.model.Team
import kotlinx.android.synthetic.main.item_player.view.*
import kotlinx.android.synthetic.main.item_team.view.*
import org.jetbrains.anko.*

class PlayerAdapter (private val players: List<Player>, private val clicklistener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        //return TeamViewHolder(SingleTeamUI().createView(AnkoContext.create(parent.context, parent)))
        return PlayerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false))
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(players[position],clicklistener)
    }

}

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view){
    private val playerPic: ImageView = view.player_pic
    private val playerName: TextView = view.player_name
    private val playerPosition: TextView = view.player_position

    fun bindItem(player: Player, clicklistener: (Player) -> Unit){
        if(player.playerPic != "") Picasso.get().load(player.playerPic).into(playerPic)
        playerName.text = player.playerName
        playerPosition.text = player.playerPosition

        itemView.setOnClickListener { clicklistener(player) }
    }
}