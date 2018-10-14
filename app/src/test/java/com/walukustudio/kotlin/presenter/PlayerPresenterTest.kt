package com.walukustudio.kotlin.presenter

import com.google.gson.Gson
import com.walukustudio.kotlin.TestContextProvider
import com.walukustudio.kotlin.model.Player
import com.walukustudio.kotlin.model.PlayerResponse
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.network.TheSportDBApi
import com.walukustudio.kotlin.view.PlayersView
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class PlayerPresenterTest {

    @Mock
    private
    lateinit var view: PlayersView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: PlayerPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PlayerPresenter(view,apiRepository,gson, TestContextProvider())
    }

    @Test
    fun getPlayerList() {
        val player: MutableList<Player> = mutableListOf()
        val response = PlayerResponse(player)
        val teamId = "133604"

        Mockito.`when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPlayers(teamId)),
                PlayerResponse::class.java)).thenReturn(response)

        presenter.getPlayerList(teamId)

        verify(view).showLoading()
        verify(view).showPlayerList(player)
        verify(view).hideLoading()
    }
}