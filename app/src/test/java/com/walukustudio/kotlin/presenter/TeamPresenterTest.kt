package com.walukustudio.kotlin.presenter

import com.google.gson.Gson
import com.walukustudio.kotlin.TestContextProvider
import com.walukustudio.kotlin.model.League
import com.walukustudio.kotlin.model.LeagueResponse
import com.walukustudio.kotlin.model.Team
import com.walukustudio.kotlin.model.TeamResponse
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.network.TheSportDBApi
import com.walukustudio.kotlin.view.TeamsView
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class TeamPresenterTest {

    @Mock
    private
    lateinit var view: TeamsView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: TeamPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamPresenter(view,apiRepository,gson, TestContextProvider())
    }

    @Test
    fun getTeamList() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val leagues: MutableList<League> = mutableListOf()
        val responseLeagues = LeagueResponse(leagues)
        val leagueId = "4328"

        `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeams(leagueId)),
                TeamResponse::class.java)).thenReturn(response)

        `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getLeagues()),
                LeagueResponse::class.java)).thenReturn(responseLeagues)

        presenter.getTeamList(leagueId)

        verify(view).showLoading()
        verify(view).showLeagueList(leagues)
        verify(view).showTeamList(teams)
        verify(view).hideLoading()
    }
}