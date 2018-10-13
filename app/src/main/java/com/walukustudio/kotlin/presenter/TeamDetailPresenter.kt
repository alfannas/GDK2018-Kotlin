package com.walukustudio.kotlin.presenter

import android.support.test.espresso.idling.CountingIdlingResource
import com.google.gson.Gson
import com.walukustudio.kotlin.model.LeagueResponse
import com.walukustudio.kotlin.model.TeamResponse
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.network.TheSportDBApi
import com.walukustudio.kotlin.utils.CoroutineContextProvider
import com.walukustudio.kotlin.view.TeamDetailView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamDetailPresenter(
        private val view: TeamDetailView,
        private val apiRepository: ApiRepository,
        private val gson: Gson,
        private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    private val serverIdlingResource = CountingIdlingResource("GLOBAL")

    fun getTeamDetail(teamId: String?) {
        serverIdlingResource.increment()
        view.showLoading()

        async(context.main){
            try {
                val data = bg { gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamDetail(teamId)),
                        TeamResponse::class.java) }
                view.showTeamDetail(data.await().teams)
                view.hideLoading()
            }finally {
                serverIdlingResource.decrement()
            }
        }

    }
}