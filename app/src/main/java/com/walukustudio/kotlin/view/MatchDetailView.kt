package com.walukustudio.kotlin.view

import com.walukustudio.kotlin.model.Schedule

interface MatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun showMatchDetail(data: List<Schedule>)
}