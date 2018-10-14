package com.walukustudio.kotlin.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.AdapterView
import android.widget.ProgressBar
import android.widget.Spinner
import com.google.gson.Gson
import com.walukustudio.kotlin.R
import com.walukustudio.kotlin.activities.matches.MatchActivity
import com.walukustudio.kotlin.activities.teams.TeamActivity
import com.walukustudio.kotlin.adapter.LeagueSpinAdapter
import com.walukustudio.kotlin.adapter.TeamAdapter
import com.walukustudio.kotlin.model.League
import com.walukustudio.kotlin.model.Team
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.presenter.TeamDetailPresenter
import com.walukustudio.kotlin.presenter.TeamPresenter
import com.walukustudio.kotlin.utils.invisible
import com.walukustudio.kotlin.utils.visible
import com.walukustudio.kotlin.view.TeamsView
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class FragmentTeams : Fragment(), TeamsView {

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private lateinit var toolbar: Toolbar
    private lateinit var searchView: SearchView

    private var teams: MutableList<Team> = mutableListOf()
    private var leagues: MutableList<League> = mutableListOf()
    private lateinit var presenter: TeamPresenter
    private lateinit var adapterTeam: TeamAdapter
    private lateinit var adapterLeague: LeagueSpinAdapter

    private lateinit var leagueName: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //val view = TeamUI<Fragment>().createView(AnkoContext.create(ctx,this))
        val view = inflater.inflate(R.layout.fragment_teams,container,false)
        listTeam = view.find(R.id.listTeam)
        progressBar = view.find(R.id.progressBar)
        swipeRefresh = view.find(R.id.swipeRefresh)
        spinner = view.find(R.id.spinner)
        toolbar = view.find(R.id.toolbar)

        adapterTeam = TeamAdapter(teams){
            team: Team ->  itemClick(team)
        }
        listTeam.adapter = adapterTeam

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamPresenter(this,request,gson)

        adapterLeague = LeagueSpinAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, leagues)
        spinner.adapter = adapterLeague
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                leagueName = leagues[position].leagueName.toString()
                presenter.getTeamList(leagueName)

                swipeRefresh.onRefresh {
                    presenter.getTeamList(leagueName)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        presenter.getTeamList("")

        setupToolbar(view)

        return view
    }

    private fun setupToolbar(view: View){
        toolbar = view.find(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Football Apps"
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

                val templist : MutableList<Team> = mutableListOf()

                for (temp in teams) {
                    if(temp.teamName != null){
                        if (temp.teamName!!.toLowerCase().contains(s.toLowerCase())) {
                            templist.add(temp)
                        }
                    }
                }

                adapterTeam = TeamAdapter(templist){
                    team: Team ->  itemClick(team)
                }
                listTeam.adapter = adapterTeam

                return true
            }
        })

    }

    private fun itemClick(team:Team){
        startActivity<TeamActivity>("id" to team.teamId)
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

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapterTeam.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(): FragmentTeams = FragmentTeams()
    }
}