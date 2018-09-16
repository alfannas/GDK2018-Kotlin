package com.walukustudio.kotlin.presenter

import com.google.gson.Gson
import com.walukustudio.kotlin.model.TeamResponse
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.network.TheSportDBApi
import com.walukustudio.kotlin.view.MainView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter(
        private val view: MainView,
        private val apiRepository: ApiRepository,
        private val gson: Gson) {

    fun getTeamList(league: String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeams(league)),
                    TeamResponse::class.java)

            uiThread {
                view.hideLoading()
                view.showTeamList(data.teamsFootbal)
            }
        }
    }
}