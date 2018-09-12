package com.walukustudio.kotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import com.walukustudio.kotlin.adapter.ScheduleAdapter
import com.walukustudio.kotlin.model.Schedule
import com.walukustudio.kotlin.network.ApiRepository
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

    private var schedules: MutableList<Schedule> = mutableListOf()
    private lateinit var presenter: SchedulePresenter
    private lateinit var adapter: ScheduleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = ScheduleUI<Fragment>().createView(AnkoContext.create(ctx,this))
        listTeam = view.find(R.id.listTeam)
        progressBar = view.find(R.id.progressBar)
        swipeRefresh = view.find(R.id.swipeRefresh)


        adapter = ScheduleAdapter(ctx,schedules,BuildConfig.NEXT){
            schedule: Schedule ->  itemClick(schedule)
        }
        listTeam.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = SchedulePresenter(this,request,gson)
        presenter.getScheduleList("4328",BuildConfig.NEXT)

        swipeRefresh.onRefresh {
            presenter.getScheduleList("4328",BuildConfig.NEXT)
        }

        return view
    }

    private fun itemClick(schedule:Schedule){
        startActivity<MatchActivity>("schedule" to schedule,"type" to BuildConfig.NEXT)
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
        fun newInstance(): FragmentNext = FragmentNext()
    }
}