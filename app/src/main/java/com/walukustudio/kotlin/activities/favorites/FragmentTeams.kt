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
import com.walukustudio.kotlin.activities.teams.TeamActivity
import com.walukustudio.kotlin.adapter.FavoriteTeamAdapter
import com.walukustudio.kotlin.adapter.TeamAdapter
import com.walukustudio.kotlin.model.FavoriteTeam
import com.walukustudio.kotlin.model.Team
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

class FragmentTeams : Fragment() {

    private var teams: MutableList<FavoriteTeam> = mutableListOf()
    private lateinit var adapter: FavoriteTeamAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var listEvent: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fav_team,container,false)
        listEvent = view.find(R.id.listTeam)
        progressBar = view.find(R.id.progressBar)
        swipeRefresh = view.find(R.id.swipeRefresh)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progressBar.invisible()
        adapter = FavoriteTeamAdapter(ctx,teams){
            team: FavoriteTeam ->  itemClick(team)
        }
        listEvent.adapter = adapter
        showFavorite()
        swipeRefresh.onRefresh {
            teams.clear()
            showFavorite()
        }
    }

    private fun itemClick(favorite: FavoriteTeam){
        startActivity<TeamActivity>("id" to favorite.teamId)
    }

    private fun showFavorite(){
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
            val team = result.parseList(classParser<FavoriteTeam>())
            teams.addAll(team)
            adapter.notifyDataSetChanged()
        }
    }

    companion object {
        fun newInstance(): FragmentFavorites = FragmentFavorites()
    }
}