package com.walukustudio.kotlin.activities

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.walukustudio.kotlin.BuildConfig
import com.walukustudio.kotlin.R
import com.walukustudio.kotlin.model.*
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.network.TheSportDBApi
import com.walukustudio.kotlin.presenter.MatchPresenter
import com.walukustudio.kotlin.utils.*
import com.walukustudio.kotlin.view.MatchDetailView
import kotlinx.android.synthetic.main.activity_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MatchActivity : AppCompatActivity(),MatchDetailView {
    private val request = ApiRepository()
    private val gson = Gson()

    private lateinit var presenter: MatchPresenter
    private lateinit var match: Schedule
    private lateinit var id: String
    private lateinit var type: String

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        val intent = intent
        id = intent.getStringExtra("id")
        type = intent.getStringExtra("type")

        presenter = MatchPresenter(this,request,gson)
        presenter.getMatchDetail(id)

        favoriteState()
        setupToolbar()
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Match Detail"
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
            val result = select(Favorite.TABLE_FAVORITE)
                    .whereArgs("(MATCH_ID = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                        Favorite.TEAM_HOME_ID to match.idHomeTeam,
                        Favorite.MATCH_ID to match.idEvent,
                        Favorite.MATCH_TYPE to type,
                        Favorite.MATCH_DATE to match.dateEvent,
                        Favorite.TEAM_AWAY_ID to match.idAwayTeam,
                        Favorite.TEAM_HOME_NAME to match.homeTeam,
                        Favorite.TEAM_AWAY_NAME to match.awayTeam,
                        Favorite.TEAM_HOME_SCORE to match.homeScore,
                        Favorite.TEAM_AWAY_SCORE to match.awayScore)
            }
            toast("Added to favorite").show()
        }catch (e: SQLiteConstraintException){
            toast(e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(MATCH_ID = {id})", "id" to id)
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

    override fun showMatchDetail(data: List<Schedule>) {
        match = data[0]
        tv_date.text = dateConvert(match.dateEvent!!)

        tv_score_home.text = match.homeScore
        tv_score_away.text = match.awayScore

        tv_team_home.text = match.homeTeam
        tv_team_away.text = match.awayTeam

        tv_goal_home.text = match.homeGoalDetails
        tv_goal_away.text = match.awayGoalDetails

        tv_shot_home.text = match.homeShots
        tv_shot_away.text = match.awayShots

        tv_keeper_home.text = match.homeKeeper
        tv_keeper_away.text = match.awayKeeper

        tv_defense_home.text = match.homeDefense
        tv_defense_away.text = match.awayDefense

        tv_midfield_home.text = match.homeMidfield
        tv_midfield_away.text = match.awayMidfield

        tv_forward_home.text = match.homeForward
        tv_forward_away.text = match.awayForward

        tv_substitute_home.text = match.homeSubstitutes
        tv_substitute_away.text = match.awaySubstitutes

        if(type.equals(BuildConfig.NEXT) && match.idHomeTeam != null && match.idAwayTeam != null){
            score_wrapper.gone()
            loadBadge(match.idHomeTeam,iv_home)
            loadBadge(match.idAwayTeam,iv_away)
        }else{
            badge_wrapper.gone()
        }
    }
}