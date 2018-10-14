package com.walukustudio.kotlin.presenter

import com.google.gson.Gson
import com.walukustudio.kotlin.TestContextProvider
import com.walukustudio.kotlin.model.Team
import com.walukustudio.kotlin.model.TeamResponse
import com.walukustudio.kotlin.network.ApiRepository
import com.walukustudio.kotlin.network.TheSportDBApi
import com.walukustudio.kotlin.view.TeamDetailView
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
class TeamDetailPresenterTest {

    @Mock
    private
    lateinit var view: TeamDetailView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: TeamDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamDetailPresenter(view,apiRepository,gson, TestContextProvider())
    }

    @Test
    fun getTeamDetail() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val teamId = "133604"

        `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamDetail(teamId)),
                TeamResponse::class.java)).thenReturn(response)

        presenter.getTeamDetail(teamId)

        verify(view).showLoading()
        verify(view).showTeamDetail(teams)
        verify(view).hideLoading()
    }
}