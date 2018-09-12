package com.walukustudio.kotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.walukustudio.kotlin.BuildConfig
import com.walukustudio.kotlin.R
import com.walukustudio.kotlin.model.Schedule
import kotlinx.android.synthetic.main.item_schedule.view.*
import java.text.SimpleDateFormat
import java.util.*

class ScheduleAdapter (private val context: Context, private val schedules: List<Schedule>, private val type: String, private val clicklistener: (Schedule) -> Unit)
    : RecyclerView.Adapter<ScheduleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        //return ScheduleViewHolder(ItemScheduleUI().createView(AnkoContext.create(parent.context, parent)))
        return ScheduleViewHolder(LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false))
    }

    override fun getItemCount(): Int {
        return schedules.size
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bindItem(schedules[position],type,clicklistener)
    }


}

class ScheduleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvTop: TextView = view.tv_top
    private val tvLeft : TextView = view.tv_left
    private val tvMiddle : TextView = view.tv_middle
    private val tvRight : TextView = view.tv_right

    fun bindItem(schedule : Schedule,type: String, clicklistener: (Schedule) -> Unit) {

        val score: String = when(type){
            BuildConfig.PAST -> schedule.homeScore + "  vs  " + schedule.awayScore
            BuildConfig.NEXT -> " vs "
            else -> "Undefined"
        }

        tvTop.text = dateConvert(schedule.dateEvent!!)
        tvLeft.text = schedule.homeTeam
        tvMiddle.text = score
        tvRight.text = schedule.awayTeam

        itemView.setOnClickListener { clicklistener(schedule) }
    }

    private fun dateConvert(date:String):String{
        val locale = Locale("id")

        val input = SimpleDateFormat("dd/MM/yy")
        val output = SimpleDateFormat("E, dd MMM yyyy",locale)

        val indate:Date = input.parse(date)

        return output.format(indate)
    }
}