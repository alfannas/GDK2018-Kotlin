package com.walukustudio.kotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.walukustudio.kotlin.R.id.*
import com.walukustudio.kotlin.model.Favorite
import org.jetbrains.anko.*

class FavoriteTeamsAdapter(private val favorites: List<Favorite>, private val listener:(Favorite) -> Unit)
    :RecyclerView.Adapter<FavoriteViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FavoriteViewHolder {
        return FavoriteViewHolder(TeamUI().createView(AnkoContext.create(p0.context, p0)))
    }

    override fun onBindViewHolder(p0: FavoriteViewHolder, p1: Int) {
        p0.bindItem(favorites[p1],listener)
    }

    override fun getItemCount(): Int {
        return favorites.size
    }
}
//class TeamUI : AnkoComponent<ViewGroup> {
//    override fun createView(ui: AnkoContext<ViewGroup>): View {
//        return with(ui) {
//            linearLayout{
//                lparams(width = matchParent, height = wrapContent)
//                padding = dip(16)
//                orientation = LinearLayout.HORIZONTAL
//
//                imageView {
//                    id = team_badge
//                }.lparams{
//                    height = dip(50)
//                    width = dip(50)
//                }
//
//                textView {
//                    id = team_name
//                    textSize = 16f
//                }.lparams{
//                    margin = dip(15)
//                }
//
//            }
//        }
//    }
//
//}
class FavoriteViewHolder(view: View) :RecyclerView.ViewHolder(view){
    private val teamBadge: ImageView = view.find(team_badge)
    private val teamName: TextView = view.find(team_name)

    fun bindItem(favorite: Favorite, listener: (Favorite) -> Unit){
        Picasso.get().load(favorite.teamBadge).into(teamBadge)
        teamName.text = favorite.teamName
        itemView.setOnClickListener { listener(favorite) }
    }
}