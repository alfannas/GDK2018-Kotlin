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
import com.walukustudio.kotlin.model.Team
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.presenter.MainPresenter
import com.walukustudio.kotlin.ui.FragmentUI
import com.walukustudio.kotlin.utils.invisible
import com.walukustudio.kotlin.utils.visible
import com.walukustudio.kotlin.view.MainView
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

import kotlinx.android.synthetic.main.fragment_prev.*
import kotlinx.android.synthetic.main.fragment_prev.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout


class FragmentPrev : Fragment(),MainView {

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MainAdapter

    private lateinit var leagueName: String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = FragmentUI<Fragment>().createView(AnkoContext.create(ctx,this))
        spinner = view.find(R.id.spinner)
        listTeam = view.find(R.id.listTeam)
        progressBar = view.find(R.id.progressBar)
        swipeRefresh = view.find(R.id.swipeRefresh)

        adapter = MainAdapter(teams)
        listTeam.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = MainPresenter(this,request,gson)

        val spinnerItems = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        swipeRefresh.onRefresh {
            presenter.getTeamList(leagueName)
        }

        return view
    }



    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showScore() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideScore() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(): FragmentPrev = FragmentPrev()
    }
}