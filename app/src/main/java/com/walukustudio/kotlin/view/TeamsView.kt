package com.walukustudio.kotlin.view

import com.walukustudio.kotlin.model.League
import com.walukustudio.kotlin.model.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data: List<League>)
    fun showTeamList(data: List<Team>)
}