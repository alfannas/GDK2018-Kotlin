package com.walukustudio.kotlin.presenter

import com.google.gson.Gson
import com.walukustudio.kotlin.BuildConfig
import com.walukustudio.kotlin.model.ScheduleResponse
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.network.TheSportDBApi
import com.walukustudio.kotlin.view.ScheduleView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SchedulePresenter (
        private val view: ScheduleView,
        private val apiRepository: ApiRepository,
        private val gson: Gson) {

    fun getScheduleList(id: String?,type:String?) {
        view.showLoading()
        doAsync {
            val data = when (type){
                BuildConfig.PAST -> gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPastSchedule(id)),
                        ScheduleResponse::class.java)
                BuildConfig.NEXT -> gson.fromJson(apiRepository.doRequest(TheSportDBApi.getNextSchedule(id)),
                        ScheduleResponse::class.java)
                else -> gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPastSchedule(id)),
                        ScheduleResponse::class.java)
            }

            uiThread {
                view.hideLoading()
                //Log.d("SchedulePresenter",data.events[0].homeTeam)
                view.showScheduleList(data.events)
            }
        }
    }
}