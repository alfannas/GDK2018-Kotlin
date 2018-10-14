package com.walukustudio.kotlin.presenter

import android.support.test.espresso.idling.CountingIdlingResource
import com.google.gson.Gson
import com.walukustudio.kotlin.BuildConfig
import com.walukustudio.kotlin.model.League
import com.walukustudio.kotlin.model.LeagueResponse
import com.walukustudio.kotlin.model.ScheduleResponse
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.network.TheSportDBApi
import com.walukustudio.kotlin.utils.CoroutineContextProvider
import com.walukustudio.kotlin.view.ScheduleView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class SchedulePresenter (
        private val view: ScheduleView,
        private val apiRepository: ApiRepository,
        private val gson: Gson,
        private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    private val serverIdlingResource = CountingIdlingResource("GLOBAL")

    fun getScheduleList(id: String?,type:String?) {
        serverIdlingResource.increment()
        view.showLoading()

        async(context.main){
            try {
                val league = bg { gson.fromJson(apiRepository.doRequest(TheSportDBApi.getLeagues()),
                        LeagueResponse::class.java) }
                val data = when(type){
                    BuildConfig.PAST -> bg {gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPastSchedule(id)),
                            ScheduleResponse::class.java)}
                    BuildConfig.NEXT -> bg { gson.fromJson(apiRepository.doRequest(TheSportDBApi.getNextSchedule(id)),
                            ScheduleResponse::class.java) }
                    else -> bg { gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPastSchedule(id)),
                            ScheduleResponse::class.java) }
                }

                val leagueFiltered = league.await().leagues.filter { it.leagueSport.equals("Soccer",true) }
                view.showLeagueList(leagueFiltered)
                view.showScheduleList(data.await().events)
                view.hideLoading()
            }finally {
                serverIdlingResource.decrement()
            }
        }

    }
}