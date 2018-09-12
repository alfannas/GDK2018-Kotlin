package com.walukustudio.kotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import com.walukustudio.kotlin.R.array.league
import com.walukustudio.kotlin.R.color.colorAccent
import com.walukustudio.kotlin.adapter.MainAdapter
import com.walukustudio.kotlin.adapter.ScheduleAdapter
import com.walukustudio.kotlin.model.Schedule
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.presenter.SchedulePresenter
import com.walukustudio.kotlin.ui.ScheduleUI
import com.walukustudio.kotlin.utils.invisible
import com.walukustudio.kotlin.utils.visible
import com.walukustudio.kotlin.view.ScheduleView

import kotlinx.android.synthetic.main.fragment_prev.*
import kotlinx.android.synthetic.main.fragment_prev.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*


class FragmentPrev : Fragment(), ScheduleView {


    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private var schedules: MutableList<Schedule> = mutableListOf()
    private lateinit var presenter: SchedulePresenter
    private lateinit var adapter: ScheduleAdapter

    private lateinit var leagueId: String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = ScheduleUI<Fragment>().createView(AnkoContext.create(ctx,this))
        listTeam = view.find(R.id.listTeam)
        progressBar = view.find(R.id.progressBar)
        swipeRefresh = view.find(R.id.swipeRefresh)


        adapter = ScheduleAdapter(ctx,schedules,BuildConfig.PAST){
            schedule: Schedule ->  itemClick(schedule)
        }
        listTeam.adapter = adapter


        val request = ApiRepository()
        val gson = Gson()
        presenter = SchedulePresenter(this,request,gson)
        presenter.getScheduleList("4328","past")

        swipeRefresh.onRefresh {
            presenter.getScheduleList("4328","past")
        }

        return view
    }

    private fun itemClick(schedule:Schedule){
        startActivity<MatchActivity>("schedule" to schedule)
        Toast.makeText(context,schedule.idEvent,Toast.LENGTH_SHORT).show()
    }


    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showScheduleList(data: List<Schedule>) {
        swipeRefresh.isRefreshing = false
        schedules.clear()
        schedules.addAll(data)
        adapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(): FragmentPrev = FragmentPrev()
    }
}