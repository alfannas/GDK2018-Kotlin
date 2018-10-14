package com.walukustudio.kotlin.activities.teams

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ProgressBar
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.walukustudio.kotlin.R
import com.walukustudio.kotlin.adapter.TabPagerAdapter
import com.walukustudio.kotlin.model.*
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.network.TheSportDBApi
import com.walukustudio.kotlin.presenter.TeamDetailPresenter
import com.walukustudio.kotlin.utils.database
import com.walukustudio.kotlin.utils.gone
import com.walukustudio.kotlin.utils.visible
import com.walukustudio.kotlin.view.TeamDetailView
import kotlinx.android.synthetic.main.activity_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class TeamActivity: AppCompatActivity(),TeamDetailView {

    private val request = ApiRepository()
    private val gson = Gson()

    private lateinit var presenter: TeamDetailPresenter
    private lateinit var id: String
    private lateinit var team: Team

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_team)

        val intent = intent
        id = intent.getStringExtra("id")

        presenter = TeamDetailPresenter(this,request,gson)
        presenter.getTeamDetail(id)

        favoriteState()
        setupToolbar()
    }

    private fun setupContent(team: Team){
        loadBadge(team.teamId,team_badge)
        team_name.text = team.teamName
        team_established.text = team.teamEstablished
        team_stadium.text = team.teamStadium
    }

    private fun loadBadge(teamId: String?,iv: ImageView) {
        if(teamId != null){
            doAsync {
                val data = gson.fromJson(request.doRequest(TheSportDBApi.getTeamDetail(teamId)),
                        TeamResponse::class.java)
                val teams : List<Team> = data.teams

                uiThread {
                    Picasso.get().load(teams[0].teamBadge).into(iv)

                }
            }
        }
    }

    private fun setupToolbar(){

        // Set the toolbar as support action bar
        setSupportActionBar(toolbar)

        // Now get the support action bar
        val actionBar = supportActionBar

        // Set toolbar title/app title
        actionBar?.title = ""

        // Display the app icon in action bar/toolbar
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu,menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if(isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(FavoriteTeam.TABLE_FAVORITE_TEAM,
                        FavoriteTeam.TEAM_ID to team.teamId,
                        FavoriteTeam.TEAM_NAME to team.teamName,
                        FavoriteTeam.TEAM_ESTABLISHED to team.teamEstablished,
                        FavoriteTeam.TEAM_STADIUM to team.teamStadium,
                        FavoriteTeam.TEAM_DESCRIPTION to team.teamDescription,
                        FavoriteTeam.TEAM_BADGE to team.teamBadge)
            }
            toast("Added to favorite").show()
        }catch (e: SQLiteConstraintException){
            toast(e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(FavoriteTeam.TABLE_FAVORITE_TEAM, "(TEAM_ID = {id})", "id" to id)
            }
            toast("Removed from favorite").show()
        }catch (e: SQLiteConstraintException){
            toast(e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.gone()
    }

    override fun showTeamDetail(data: List<Team>) {
        team = data[0]
        setupContent(team)

        val bundleOverview = Bundle()
        bundleOverview.putString("overview", team.teamDescription)
        val fragmentOverview = FragmentOverview()
        fragmentOverview.arguments = bundleOverview

        val bundlePlayers = Bundle()
        bundlePlayers.putString("teamId", team.teamId)
        val fragmentPlayers = FragmentPlayers()
        fragmentPlayers.arguments = bundlePlayers

        val fragmentAdapter = TabPagerAdapter(supportFragmentManager, fragmentOverview, fragmentPlayers,
                "Overview","Players")
        viewpager_main.adapter = fragmentAdapter

        tabs_main.setupWithViewPager(viewpager_main)
    }

}