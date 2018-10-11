package com.walukustudio.kotlin.presenter

import android.support.test.espresso.idling.CountingIdlingResource
import com.google.gson.Gson
import com.walukustudio.kotlin.model.LeagueResponse
import com.walukustudio.kotlin.model.TeamResponse
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.network.TheSportDBApi
import com.walukustudio.kotlin.utils.CoroutineContextProvider
import com.walukustudio.kotlin.view.TeamView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamPresenter(
        private val view: TeamView,
        private val apiRepository: ApiRepository,
        private val gson: Gson,
        private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    private val serverIdlingResource = CountingIdlingResource("GLOBAL")

    fun getTeamList(lid: String?) {
        serverIdlingResource.increment()
        view.showLoading()

        async(context.main){
            try {
                val league = bg { gson.fromJson(apiRepository.doRequest(TheSportDBApi.getLeagues()),
                        LeagueResponse::class.java) }
                val data = bg { gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeams(lid)),
                        TeamResponse::class.java) }

                val leagueFiltered = league.await().leagues.filter { it.leagueSport.equals("Soccer",true) }
                view.showLeagueList(leagueFiltered)
                view.showTeamList(data.await().teams)
                view.hideLoading()
            }finally {
                serverIdlingResource.decrement()
            }
        }

    }
}