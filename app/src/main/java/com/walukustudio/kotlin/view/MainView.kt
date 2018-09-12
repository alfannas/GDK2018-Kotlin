package com.walukustudio.kotlin.view

import com.walukustudio.kotlin.model.Team

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}