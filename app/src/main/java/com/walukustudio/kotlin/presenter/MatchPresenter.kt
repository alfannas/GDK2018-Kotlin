package com.walukustudio.kotlin.presenter

import com.google.gson.Gson
import com.walukustudio.kotlin.model.ScheduleResponse
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.network.TheSportDBApi
import com.walukustudio.kotlin.utils.CoroutineContextProvider
import com.walukustudio.kotlin.view.MatchDetailView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchPresenter (private val view: MatchDetailView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()){
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
        async(context.main){
            val data = bg { gson.fromJson(apiRepository.doRequest(TheSportDBApi.getScheduleDetail(eventId)),
                    ScheduleResponse::class.java) }
            view.showMatchDetail(data.await().events)
            view.hideLoading()
        }
    }
}