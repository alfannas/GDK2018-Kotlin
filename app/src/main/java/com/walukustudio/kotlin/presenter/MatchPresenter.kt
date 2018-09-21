package com.walukustudio.kotlin.presenter

import android.support.test.espresso.idling.CountingIdlingResource
import com.google.gson.Gson
import com.walukustudio.kotlin.model.ScheduleResponse
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.network.TheSportDBApi
import com.walukustudio.kotlin.utils.CoroutineContextProvider
import com.walukustudio.kotlin.view.MatchDetailView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchPresenter (private val view: MatchDetailView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()){

    private val serverIdlingResource = CountingIdlingResource("NetworkCallMatch")

    fun getMatchDetail(eventId: String){
        view.showLoading()
//        doAsync {
//            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getScheduleDetail(eventId)),
//                    ScheduleResponse::class.java)
//
//            uiThread {
//                view.hideLoading()
//                view.showMatchDetail(data.events)
//            }
//        }
        serverIdlingResource.increment()
        async(context.main){
            val data = bg { gson.fromJson(apiRepository.doRequest(TheSportDBApi.getScheduleDetail(eventId)),
                    ScheduleResponse::class.java) }
            view.showMatchDetail(data.await().events)
            view.hideLoading()
            serverIdlingResource.decrement()
        }
    }
}