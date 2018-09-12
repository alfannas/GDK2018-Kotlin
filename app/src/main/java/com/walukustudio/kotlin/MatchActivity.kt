package com.walukustudio.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ImageView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.walukustudio.kotlin.model.Schedule
import com.walukustudio.kotlin.model.Team
import com.walukustudio.kotlin.model.TeamResponse
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.network.TheSportDBApi
import com.walukustudio.kotlin.utils.dateConvert
import com.walukustudio.kotlin.utils.gone
import kotlinx.android.synthetic.main.activity_match.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchActivity : AppCompatActivity() {
    private val request = ApiRepository()
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        val intent = intent
        val schedule: Schedule = intent.getParcelableExtra("schedule")
        val type: String = intent.getStringExtra("type")

        setupContent(schedule,type)

        setupToolbar()
    }

    private fun setupContent(schedule:Schedule,type:String){
        tv_date.text = dateConvert(schedule.dateEvent!!)

        tv_score_home.text = schedule.homeScore
        tv_score_away.text = schedule.awayScore

        tv_team_home.text = schedule.homeTeam
        tv_team_away.text = schedule.awayTeam

        tv_goal_home.text = schedule.homeGoalDetails
        tv_goal_away.text = schedule.awayGoalDetails

        tv_shot_home.text = schedule.homeShots
        tv_shot_away.text = schedule.awayShots

        tv_keeper_home.text = schedule.homeKeeper
        tv_keeper_away.text = schedule.awayKeeper

        tv_defense_home.text = schedule.homeDefense
        tv_defense_away.text = schedule.awayDefense

        tv_midfield_home.text = schedule.homeMidfield
        tv_midfield_away.text = schedule.awayMidfield

        tv_forward_home.text = schedule.homeForward
        tv_forward_away.text = schedule.awayForward

        tv_substitute_home.text = schedule.homeSubstitutes
        tv_substitute_away.text = schedule.awaySubstitutes

        if(type.equals(BuildConfig.NEXT)){
            score_wrapper.gone()
            loadBadge(schedule.idHomeTeam!!,iv_home)
            loadBadge(schedule.idAwayTeam!!,iv_away)
        }else{
            badge_wrapper.gone()
        }
    }

    private fun loadBadge(teamId: String,iv: ImageView) {
        doAsync {
            val data = gson.fromJson(request.doRequest(TheSportDBApi.getTeamDetail(teamId)),
                    TeamResponse::class.java)

            val teams : List<Team> = data.teams

            uiThread {
                Picasso.get().load(teams[0].teamBadge).into(iv)
            }
        }
    }

    private fun setupToolbar(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Match Detail"
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}