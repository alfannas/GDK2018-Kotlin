package com.walukustudio.kotlin.view

import com.walukustudio.kotlin.model.Team

interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(data: List<Team>)
}