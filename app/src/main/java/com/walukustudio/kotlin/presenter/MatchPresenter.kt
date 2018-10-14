package com.walukustudio.kotlin.presenter

import android.support.test.espresso.idling.CountingIdlingResource
import android.util.Log
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

    private val serverIdlingResource = CountingIdlingResource("GLOBAL")

    fun getMatchDetail(eventId: String){
        serverIdlingResource.increment()
        view.showLoading()

        async(context.main){
            try {
                Log.d("MatchPresenter", serverIdlingResource.isIdleNow.toString())
                val data = bg {
                    gson.fromJson(apiRepository.doRequest(TheSportDBApi.getScheduleDetail(eventId)),
                            ScheduleResponse::class.java)
                }
                view.showMatchDetail(data.await().events)
                view.hideLoading()
            }finally {
                serverIdlingResource.decrement()
                Log.d("MatchPresenter",serverIdlingResource.isIdleNow.toString())
            }

        }
    }

}