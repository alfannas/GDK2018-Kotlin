package com.walukustudio.kotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.google.gson.Gson
import com.walukustudio.kotlin.adapter.FavoriteAdapter
import com.walukustudio.kotlin.model.Favorite
import com.walukustudio.kotlin.model.Schedule
import com.walukustudio.kotlin.model.ScheduleResponse
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.network.TheSportDBApi
import com.walukustudio.kotlin.ui.ScheduleUI
import com.walukustudio.kotlin.utils.database
import com.walukustudio.kotlin.utils.invisible
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class FragmentFav : Fragment() {


    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var adapter: FavoriteAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var listEvent: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = ScheduleUI<Fragment>().createView(AnkoContext.create(ctx,this))
        listEvent = view.find(R.id.listTeam)
        progressBar = view.find(R.id.progressBar)
        swipeRefresh = view.find(R.id.swipeRefresh)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progressBar.invisible()
        adapter = FavoriteAdapter(ctx,favorites){
            favorite: Favorite ->  itemClick(favorite)
        }
        listEvent.adapter = adapter
        showFavorite()
        swipeRefresh.onRefresh {
            favorites.clear()
            showFavorite()
        }
    }

    private fun itemClick(favorite: Favorite){
        startActivity<MatchActivity>(
                "id" to favorite.eventId,
                "type" to favorite.eventType)
    }

    private fun showFavorite(){
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            var result = select(Favorite.TABLE_FAVORITE)
            var favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }
    private fun loadSchedule(eventId : String): Schedule{
        val gson = Gson()
        val request = ApiRepository()
        val data = gson.fromJson(request.doRequest(TheSportDBApi.getScheduleDetail(eventId)),
                ScheduleResponse::class.java)

        return data.events[0]
    }
    companion object {
        fun newInstance(): FragmentFav = FragmentFav()
    }
}