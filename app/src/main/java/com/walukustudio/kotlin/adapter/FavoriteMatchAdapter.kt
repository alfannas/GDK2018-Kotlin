package com.walukustudio.kotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.walukustudio.kotlin.R
import com.walukustudio.kotlin.model.FavoriteMatch
import com.walukustudio.kotlin.utils.*
import kotlinx.android.synthetic.main.item_schedule.view.*
import java.util.*

class FavoriteMatchAdapter (private val context: Context, private val favorites: List<FavoriteMatch>, private val clicklistener: (FavoriteMatch) -> Unit)
    : RecyclerView.Adapter<FavScheduleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavScheduleViewHolder {
        return FavScheduleViewHolder(LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false))
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    override fun onBindViewHolder(holder: FavScheduleViewHolder, position: Int) {
        holder.bindItem(favorites[position],clicklistener)
    }
}

class FavScheduleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvTop: TextView = view.tv_top
    private val tvTime: TextView = view.tv_time
    private val tvLeft : TextView = view.tv_left
    private val tvMiddle : TextView = view.tv_middle
    private val tvRight : TextView = view.tv_right

    fun bindItem(favorite : FavoriteMatch, clicklistener: (FavoriteMatch) -> Unit) {

        val score = if(favorite.teamHomeScore != null && favorite.teamAwayScore != null){
            "${favorite.teamHomeScore}  vs  ${favorite.teamAwayScore}"
        }else{
            " vs "
        }

        val dateUT: Date = stringToDate(favorite.eventDate, getNormalizedTime(favorite.eventTime))
        val dateLocal: Date = dateFromUTC(dateUT)

        val dateTime = getDateTime(dateLocal)

        tvTop.text = dateConvert(dateTime[0])
        tvTime.text = timeConvert(dateTime[1])
        tvLeft.text = favorite.teamHomeName
        tvMiddle.text = score
        tvRight.text = favorite.teamAwayName

        itemView.setOnClickListener { clicklistener(favorite) }
    }
}