package com.walukustudio.kotlin

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.walukustudio.kotlin.ui.ClubItemUI
import kotlinx.android.extensions.LayoutContainer
import org.jetbrains.anko.AnkoContext

/**
 * Created by alfannas on 1/9/2018.
 */
class RecyclerViewAdapter (private val items: List<Item>, private val clicklistener: (Item) -> Unit)
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ClubItemUI().createView(AnkoContext.create(parent.context,parent)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position],clicklistener)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        var tvName: TextView
        var ivClub: ImageView

        init {
            tvName = itemView.findViewById(ClubItemUI.tvClubName)
            ivClub = itemView.findViewById(ClubItemUI.ivClubImage)
        }

        fun bindItem(items: Item, clicklistener: (Item) -> Unit){
            tvName.text = items.name
            Glide.with(itemView.context).load(items.image).into(ivClub)
            itemView.setOnClickListener { clicklistener(items) }
        }


    }
}