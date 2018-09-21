package com.walukustudio.kotlin.presenter

import android.support.test.espresso.idling.CountingIdlingResource
import com.google.gson.Gson
import com.walukustudio.kotlin.BuildConfig
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

    private val serverIdlingResource = CountingIdlingResource("NetworkCallSchedule")

    fun getScheduleList(id: String?,type:String?) {
        view.showLoading()
//        doAsync {
//            val data = when (type){
//                BuildConfig.PAST -> gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPastSchedule(id)),
//                        ScheduleResponse::class.java)
//                BuildConfig.NEXT -> gson.fromJson(apiRepository.doRequest(TheSportDBApi.getNextSchedule(id)),
//                        ScheduleResponse::class.java)
//                else -> gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPastSchedule(id)),
//                        ScheduleResponse::class.java)
//            }
//
//            uiThread {
//                view.hideLoading()
//                //Log.d("SchedulePresenter",data.events[0].homeTeam)
//                view.showScheduleList(data.events)
//            }
//        }
        serverIdlingResource.increment()
        async(context.main){
            val data = when(type){
                BuildConfig.PAST -> bg {gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPastSchedule(id)),
                        ScheduleResponse::class.java)}
                BuildConfig.NEXT -> bg { gson.fromJson(apiRepository.doRequest(TheSportDBApi.getNextSchedule(id)),
                        ScheduleResponse::class.java) }
                else -> bg { gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPastSchedule(id)),
                        ScheduleResponse::class.java) }
            }
            view.showScheduleList(data.await().events)
            view.hideLoading()
            serverIdlingResource.decrement()
        }
    }

}