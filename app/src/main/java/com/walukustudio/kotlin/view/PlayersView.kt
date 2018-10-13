package com.walukustudio.kotlin.view

import com.walukustudio.kotlin.model.Player

interface PlayersView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerList(data: List<Player>)
}