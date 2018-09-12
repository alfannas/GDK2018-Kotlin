package com.walukustudio.kotlin.view

import com.walukustudio.kotlin.model.Schedule

interface ScheduleView {
    fun showLoading()
    fun hideLoading()
    fun showScheduleList(data: List<Schedule>)
}