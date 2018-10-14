package com.walukustudio.kotlin.presenter

import android.support.test.espresso.idling.CountingIdlingResource
import com.google.gson.Gson
import com.walukustudio.kotlin.model.PlayerResponse
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.network.TheSportDBApi
import com.walukustudio.kotlin.utils.CoroutineContextProvider
import com.walukustudio.kotlin.view.PlayersView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerPresenter (
        private val view: PlayersView,
        private val apiRepository: ApiRepository,
        private val gson: Gson,
        private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    private val serverIdlingResource = CountingIdlingResource("GLOBAL")

    fun getPlayerList(teamId: String?) {
        serverIdlingResource.increment()
        view.showLoading()

        async(context.main){
            try {
                val data = bg { gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPlayers(teamId)),
                        PlayerResponse::class.java) }

                val playerFiltered = data.await().player.filter { it.playerSport.equals("Soccer",true) }
                view.showPlayerList(playerFiltered)
                view.hideLoading()
            }finally {
                serverIdlingResource.decrement()
            }
        }

    }
}