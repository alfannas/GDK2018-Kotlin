package com.walukustudio.kotlin.activities.matches

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import com.walukustudio.kotlin.BuildConfig
import com.walukustudio.kotlin.R
import com.walukustudio.kotlin.activities.MatchActivity
import com.walukustudio.kotlin.adapter.LeagueSpinAdapter
import com.walukustudio.kotlin.adapter.ScheduleAdapter
import com.walukustudio.kotlin.model.League
import com.walukustudio.kotlin.model.Schedule
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.network.TheSportDBApi
import com.walukustudio.kotlin.presenter.SchedulePresenter
import com.walukustudio.kotlin.ui.ScheduleUI
import com.walukustudio.kotlin.utils.invisible
import com.walukustudio.kotlin.utils.visible
import com.walukustudio.kotlin.view.ScheduleView
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class FragmentNext: Fragment(), ScheduleView {

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner

    private var leagues: MutableList<League> = mutableListOf()
    private var schedules: MutableList<Schedule> = mutableListOf()
    private lateinit var presenter: SchedulePresenter
    private lateinit var adapterLeague: LeagueSpinAdapter
    private lateinit var adapterSchedule: ScheduleAdapter
    private lateinit var idLeague: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = ScheduleUI<Fragment>().createView(AnkoContext.create(ctx,this))
        listTeam = view.find(R.id.listTeam)
        progressBar = view.find(R.id.progressBar)
        swipeRefresh = view.find(R.id.swipeRefresh)
        spinner = view.find(R.id.spinner)

        adapterSchedule = ScheduleAdapter(ctx,schedules, BuildConfig.NEXT){
            schedule: Schedule ->  itemClick(schedule)
        }
        listTeam.adapter = adapterSchedule

        val request = ApiRepository()
        val gson = Gson()
        presenter = SchedulePresenter(this,request,gson)

        adapterLeague = LeagueSpinAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, leagues)
        spinner.adapter = adapterLeague
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                idLeague = leagues[position].leagueId.toString()
                Log.d("FragmentNext",idLeague)
                presenter.getScheduleList(idLeague, BuildConfig.NEXT)

                swipeRefresh.onRefresh {
                    presenter.getScheduleList(idLeague, BuildConfig.NEXT)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        //idLeague = leagues[spinner.selectedItemPosition].leagueId.toString()
//        Log.d("FragmentNext1",idLeague)
        presenter.getScheduleList("0", BuildConfig.NEXT)

        return view
    }

    private fun itemClick(schedule:Schedule){
        startActivity<MatchActivity>(
                "id" to schedule.idEvent,
                "type" to BuildConfig.NEXT)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showLeagueList(data: List<League>) {
        swipeRefresh.isRefreshing = false
        leagues.clear()
        leagues.addAll(data)
        adapterLeague.notifyDataSetChanged()
    }

    override fun showScheduleList(data: List<Schedule>) {
        swipeRefresh.isRefreshing = false
        schedules.clear()
        schedules.addAll(data)
        adapterSchedule.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(): FragmentNext = FragmentNext()
    }
}