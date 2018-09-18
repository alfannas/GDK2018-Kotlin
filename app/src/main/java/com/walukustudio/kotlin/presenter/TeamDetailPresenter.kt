package com.walukustudio.kotlin.presenter

import com.google.gson.Gson
import com.walukustudio.kotlin.model.TeamResponse
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.network.TheSportDBApi
import com.walukustudio.kotlin.view.TeamDetailView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamDetailPresenter(
        private val view: TeamDetailView,
        private val apiRepository: ApiRepository,
        private val gson: Gson) {

    fun getTeamDetail(teamId: String){
        view.showLoading()
        async(UI){
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamDetail(teamId)),
                        TeamResponse::class.java)
            }

            view.showTeamDetail(data.await().teamsFootbal)
            view.hideLoading()
        }
    }
}