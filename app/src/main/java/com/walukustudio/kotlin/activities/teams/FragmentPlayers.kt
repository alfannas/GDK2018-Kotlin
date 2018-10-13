package com.walukustudio.kotlin.activities.teams

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import com.google.gson.Gson
import com.walukustudio.kotlin.BuildConfig
import com.walukustudio.kotlin.R
import com.walukustudio.kotlin.activities.matches.MatchActivity
import com.walukustudio.kotlin.adapter.LeagueSpinAdapter
import com.walukustudio.kotlin.adapter.PlayerAdapter
import com.walukustudio.kotlin.adapter.ScheduleAdapter
import com.walukustudio.kotlin.model.League
import com.walukustudio.kotlin.model.Player
import com.walukustudio.kotlin.model.Schedule
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.presenter.PlayerPresenter
import com.walukustudio.kotlin.presenter.SchedulePresenter
import com.walukustudio.kotlin.ui.OverviewUI
import com.walukustudio.kotlin.ui.PlayerUI
import com.walukustudio.kotlin.utils.invisible
import com.walukustudio.kotlin.utils.visible
import com.walukustudio.kotlin.view.PlayersView
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class FragmentPlayers : Fragment(),PlayersView {

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private var players: MutableList<Player> = mutableListOf()
    private lateinit var presenter: PlayerPresenter
    private lateinit var adapter: PlayerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val idTeam = this.arguments?.getString("teamId")

        val view = PlayerUI<Fragment>().createView(AnkoContext.create(ctx,this))
        listTeam = view.find(R.id.listTeam)
        progressBar = view.find(R.id.progressBar)
        swipeRefresh = view.find(R.id.swipeRefresh)

        adapter = PlayerAdapter(players){
            player: Player ->  itemClick(player)
        }
        listTeam.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayerPresenter(this,request,gson)
        presenter.getPlayerList(idTeam)

        swipeRefresh.onRefresh {
            presenter.getPlayerList(idTeam)
        }

        return view
    }

    private fun itemClick(player:Player){
        startActivity<PlayerDetailActivity>("player" to player)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showPlayerList(data: List<Player>) {
        swipeRefresh.isRefreshing = false
        players.clear()
        players.addAll(data)
        adapter.notifyDataSetChanged()
    }
}