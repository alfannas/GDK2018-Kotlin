package com.walukustudio.kotlin.activities.favorites

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.walukustudio.kotlin.R
import com.walukustudio.kotlin.activities.FragmentFavorites
import com.walukustudio.kotlin.activities.matches.MatchActivity
import com.walukustudio.kotlin.adapter.FavoriteMatchAdapter
import com.walukustudio.kotlin.model.FavoriteMatch
import com.walukustudio.kotlin.ui.FavoritesUI
import com.walukustudio.kotlin.utils.database
import com.walukustudio.kotlin.utils.invisible
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class FragmentMatches : Fragment() {

    private var favorites: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var adapter: FavoriteMatchAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var listEvent: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = FavoritesUI<Fragment>().createView(AnkoContext.create(ctx,this))
        listEvent = view.find(R.id.listTeam)
        progressBar = view.find(R.id.progressBar)
        swipeRefresh = view.find(R.id.swipeRefresh)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progressBar.invisible()
        adapter = FavoriteMatchAdapter(ctx,favorites){
            favorite: FavoriteMatch ->  itemClick(favorite)
        }
        listEvent.adapter = adapter
        showFavorite()
        swipeRefresh.onRefresh {
            favorites.clear()
            showFavorite()
        }
    }

    private fun itemClick(favorite: FavoriteMatch){
        startActivity<MatchActivity>(
                "id" to favorite.eventId,
                "type" to favorite.eventType)
    }

    private fun showFavorite(){
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    companion object {
        fun newInstance(): FragmentFavorites = FragmentFavorites()
    }
}