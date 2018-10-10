package com.walukustudio.kotlin.view

import com.walukustudio.kotlin.model.League
import com.walukustudio.kotlin.model.Schedule

interface ScheduleView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data: List<League>)
    fun showScheduleList(data: List<Schedule>)
}