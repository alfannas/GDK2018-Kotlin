package com.walukustudio.kotlin.activities.matches

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import android.widget.*
import com.google.gson.Gson
import com.walukustudio.kotlin.BuildConfig
import com.walukustudio.kotlin.R
import com.walukustudio.kotlin.adapter.LeagueSpinAdapter
import com.walukustudio.kotlin.adapter.ScheduleAdapter
import com.walukustudio.kotlin.model.League
import com.walukustudio.kotlin.model.Schedule
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.presenter.SchedulePresenter
import com.walukustudio.kotlin.ui.ScheduleUI
import com.walukustudio.kotlin.utils.invisible
import com.walukustudio.kotlin.utils.visible
import com.walukustudio.kotlin.view.ScheduleView

import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.*


class FragmentPrev : Fragment(), ScheduleView {

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private lateinit var searchView: SearchView

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

        adapterSchedule = ScheduleAdapter(ctx,schedules, BuildConfig.PAST){
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
                presenter.getScheduleList(idLeague, BuildConfig.PAST)

                swipeRefresh.onRefresh {
                    presenter.getScheduleList(idLeague, BuildConfig.PAST)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        //idLeague = leagues[spinner.selectedItemPosition].leagueId.toString()
//        Log.d("FragmentNext1",idLeague)
        presenter.getScheduleList("0", BuildConfig.PAST)

        setupToolbar()

        return view
    }

    private fun itemClick(schedule:Schedule){
        startActivity<MatchActivity>(
                "id" to schedule.idEvent,
                "type" to BuildConfig.PAST)
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

    private fun setupToolbar(){
        this.setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //val inflaterMenu = activity?.menuInflater
        inflater.inflate(R.menu.search, menu)

        val searchMenuItem = menu.findItem(R.id.search)
        searchView = searchMenuItem.actionView as SearchView
        searchView.queryHint = "Search"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {

                return false
            }

            override fun onQueryTextChange(s: String): Boolean {

                val templist : MutableList<Schedule> = mutableListOf()

                for (temp in schedules) {
                    if(temp.homeTeam != null && temp.awayTeam != null){
                        if (temp.homeTeam!!.toLowerCase().contains(s.toLowerCase()) ||
                                temp.awayTeam!!.toLowerCase().contains(s.toLowerCase())) {
                            templist.add(temp)
                        }
                    }
                }

                adapterSchedule = ScheduleAdapter(ctx,templist,BuildConfig.PAST){
                    schedule: Schedule ->  itemClick(schedule)
                }
                listTeam.adapter = adapterSchedule

                return true
            }
        })

    }

    companion object {
        fun newInstance(): FragmentPrev = FragmentPrev()
    }
}